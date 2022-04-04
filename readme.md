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