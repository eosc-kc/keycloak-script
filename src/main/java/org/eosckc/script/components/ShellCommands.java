package org.eosckc.script.components;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellCommands {

    @ShellMethod("Import groups from csv file")
    public String addGroups(@ShellOption(help = "Csv file name") String fileName,
                            @ShellOption(defaultValue = "https://login-demo.dissco.eu/auth/", help = "Keycloak server url") String keycloakUrl,
                            @ShellOption(help = "dissco") String realmName) {

        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            List<String[]> groupsFromCsv= reader.readAll();
        }catch (FileNotFoundException e) {
            // e.printStackTrace();
            return fileName + " does not exist.";
        } catch (CsvException | IOException e) {
            // e.printStackTrace();
            return "Something goes wrong during csv file parsing";
        }

        return "Groups have been added to "+ keycloakUrl;
    }
}
