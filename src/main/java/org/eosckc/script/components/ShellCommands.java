package org.eosckc.script.components;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@ShellComponent
public class ShellCommands {

    @ShellMethod("Import groups from csv file")
    public String addGroups(@ShellOption(help = "Csv file name") String fileName,
                            @ShellOption(defaultValue = "https://login-demo.dissco.eu/auth/", help = "Keycloak server url") String keycloakUrl,
                            @ShellOption(help = "dissco") String realmName,
                            @ShellOption(help = "admin username") String username,
                            @ShellOption(help = "admin password") String pwd) {

        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            List<String[]> groupsFromCsv= reader.readAll();

            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakUrl)
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm(realmName)
                    .clientId("admin-cli")
                    .username("admin")
                    .password(pwd)
                    .resteasyClient(
                            new ResteasyClientBuilder()
                                    .connectionPoolSize(10).build()
                    ).build();

            groupsFromCsv.stream().filter(line -> "ROR ID".equals(line[0])).map(line -> {
                GroupRepresentation group = new GroupRepresentation();
                group.setName(line[0]);
                group.singleAttribute("organizationName", line[1]);
                return group;
            }).forEach(group ->  keycloak.realm(realmName).groups().add(group));

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return fileName + " does not exist.";
        } catch (CsvException | IOException e) {
            e.printStackTrace();
            return "Something goes wrong during csv file parsing";
        }

        return "Groups have been added to "+ keycloakUrl;
    }
}
