package com.jkurapati.grocery.api.server;

import com.google.common.flogger.FluentLogger;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
import java.util.Date;
import java.util.EnumSet;
import java.util.logging.Level;

import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

public class JettyServer {
    private static final String APPLICATION_PATH = "/api";
    private static final String CONTEXT_ROOT = "/";
    private static final String PATH_ALL = "/*";
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public void start() throws Exception {
        final int port = 8080;
        final Server server = new Server(port);

        ServletContextHandler servletContextHandler = new ServletContextHandler(server, CONTEXT_ROOT, SESSIONS);
        configureRestEasy(servletContextHandler);
        servletContextHandler.addEventListener(new GuiceResteasyBootstrapServletContextListener());
        servletContextHandler.addFilter(GuiceFilter.class, PATH_ALL, EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.setInitParameter("resteasy.guice.modules", "com.jkurapati.grocery.api.server.RestEasyModule");

        // You MUST add DefaultServlet or your server will always return 404s
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        // Start the Server so it starts accepting connections from clients.
        server.start();
        logger.at(Level.INFO).log("Started server: %s", new Date());
        server.join();
    }

    private void configureRestEasy(ServletContextHandler servletContextHandler) {
        // Setup RESTEasy's HttpServletDispatcher at "/api/*".
        final ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter("resteasy.servlet.mapping.prefix",
                APPLICATION_PATH);
        servletContextHandler.addServlet(restEasyServlet, APPLICATION_PATH + PATH_ALL);
    }
}
