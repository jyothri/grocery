package com.jkurapati.grocery.api.server;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.util.Arrays;

public class ConfigModule extends AbstractModule {
    private final String[] args;

    public ConfigModule(String[] args) {
        this.args = args;
    }

    @Override
    public void configure() {
        OptionParser parser = new OptionParser();

        ArgumentAcceptingOptionSpec<Integer> portSpec =
                parser.accepts("port", "The port to listen on")
                        .withOptionalArg().ofType(Integer.class).defaultsTo(8080);

        ArgumentAcceptingOptionSpec<String> addressSpec =
                parser.accepts("address", "The address to bind to")
                        .withOptionalArg()
                        .ofType(String.class)
                        .defaultsTo("0.0.0.0");

        parser.acceptsAll(Arrays.asList("help", "?"), "This help text");

        OptionSet options = parser.parse(args);

        if (options.has("?")) {
            try {
                parser.printHelpOn(System.out);
                System.exit(0);
            } catch (IOException e) {
                // Should never happen.
                e.printStackTrace();
            }
            return;
        }

        Integer port = portSpec.value(options);
        bind(Integer.class).annotatedWith(Names.named("port")).toInstance(port);
        bind(String.class).annotatedWith(Names.named("address")).toInstance(addressSpec.value(options));
    }
}
