package com.jkurapati.grocery.api.server;

import com.google.common.flogger.FluentLogger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import java.util.Date;
import java.util.logging.Level;

public class JettyServer {
    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public void startRestEasyServer() throws Exception {
        final int port = 8080;
        final Server server = new Server(port);

        // Setup the basic Application "context" at "/".
        // This is also known as the handler tree (in Jetty speak).
        final ServletContextHandler context = new ServletContextHandler(
                server, CONTEXT_ROOT);

        // Setup RESTEasy's HttpServletDispatcher at "/api/*".
        final ServletHolder restEasyServlet = new ServletHolder(
                new HttpServletDispatcher());
        restEasyServlet.setInitParameter("resteasy.servlet.mapping.prefix",
                APPLICATION_PATH);
        restEasyServlet.setInitParameter("javax.ws.rs.Application",
                "com.jkurapati.grocery.api.resource.Application");
        context.addServlet(restEasyServlet, APPLICATION_PATH + "/*");

        // Setup the DefaultServlet at "/".
        final ServletHolder defaultServlet = new ServletHolder(
                new DefaultServlet());
        context.addServlet(defaultServlet, CONTEXT_ROOT);

        // Start the Server so it starts accepting connections from clients.
        server.start();
        logger.at(Level.INFO).log("Started server: %s", new Date());
        server.join();
    }
}
