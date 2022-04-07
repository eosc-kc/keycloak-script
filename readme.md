# Command prompt application

**This project contains Keycloak script application**.
User can execute this project as one line command tool from a folder containing produced jar.
Project is a [Spring Shell](https://docs.spring.io/spring-shell/docs/current/reference/htmlsingle) developed as Spring Boot project.
Project has helpful commands together with scripts executing calls in remote(s) rest apis.

# Quickstart

## Jar production

Running 'mvn clean package' from source will produce jar needed to execute one line command tool.
With jar this project can be executed in any environment having Java installed.

## Commands

All commands start from java -jar spa-cmd.jar . When an option value consists whitespace value must be between double quote ( for example "cmd test reservation"). In linux environments single quote is also working. If "org.springframework.web.reactive.function.client.WebClientRequestException: failed to resolve ‘xxx' after 2 queries" error return, it means that xxx is not available option.

1. java -jar keycloak-script-1.0.jar help

This command return all available commands. If user add after one command, all informations about this command are returned.
For example *java -jar spa-cmd.jar help reserve*, return information about reserve command ( create circuit from Activiti).

Example command :

    java -jar keycloak-script-1.0.jar help add-groups

2. java -jar keycloak-script-1.0.jar script --file file

Execute scripts from the specified file

Example command :

    java -jar keycloak-script-1.0.jar script --file spa.txt

3. java -jar keycloak-script-1.0.jar add-groups --file-name file-name --keycloak-url keycloak-url --realm-name realm-name

With this command, groups from 'file-nam' csv file can be added in Keycloak. Name will be the first column and organizationName the second column.

Options :
- file-name : Csv file path
- keycloak-url : Keycloak server url, default to https://login-demo.dissco.eu/auth/.
- realm-name : realm name, default to dissco.

Example command :

    java -jar keycloak-script-1.0.jar add-groups --file-name C:/old_E/groups.csv