package org.eosckc.script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

@Component
@Order(InteractiveShellApplicationRunner.PRECEDENCE - 100)
public class OneCommandApplicationRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(OneCommandApplicationRunner.class);
    private final Shell shell;
    private final ConfigurableEnvironment environment;

    public OneCommandApplicationRunner(Shell shell, ConfigurableEnvironment environment) {
        this.shell = shell;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (args.getSourceArgs().length > 1 || (args.getSourceArgs().length == 1
                && !"--spring.output.ansi.enabled=always".equals(args.getSourceArgs()[0]))) {
            InteractiveShellApplicationRunner.disable(environment);
            List<String> commands = new ArrayList<>(Arrays.asList(args.getSourceArgs()));
            // this args are getting when executed from IDE - remove it
            commands.remove("--spring.output.ansi.enabled=always");
            if ("script".equals(commands.get(0))) {
                shell.evaluate(new PredefinedInput(commands));
            } else {
                logger.warn(shell.evaluate(new PredefinedInput(commands)).toString());
                shell.evaluate(() -> "exit");
            }
        }
    }

    private class PredefinedInput implements Input {

        private final List<String> commands;

        PredefinedInput(List<String> commands) {
            this.commands = commands;
        }

        @Override
        public String rawText() {
            return StringUtils.join(commands, " ");
        }

        @Override
        public List<String> words() {
            return commands;
        }
    }

}
