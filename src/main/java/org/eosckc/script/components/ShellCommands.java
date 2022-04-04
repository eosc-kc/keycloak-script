package org.eosckc.script.components;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellCommands {

    @ShellMethod("Test method")
    public String testing() {
        return "test";
    }
}
