package com.jkurapati.grocery.api.server;

import com.google.inject.AbstractModule;
import org.eclipse.jetty.server.Server;

public class JettyModule extends AbstractModule {
    @Override
    public void configure() {
        bind(Server.class).toProvider(JettyServerProvider.class);
    }
}