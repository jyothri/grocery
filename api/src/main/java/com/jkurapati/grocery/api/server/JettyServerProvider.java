package com.jkurapati.grocery.api.server;

import com.google.common.flogger.FluentLogger;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.EnumSet;
import java.util.logging.Level;

import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;

class JettyServerProvider implements Provider<Server> {
    private static final String APPLICATION_PATH = "/api";
    private static final String CONTEXT_ROOT = "/";
    private static final String PATH_ALL = "/*";
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private final Server server;

    @Inject
    public JettyServerProvider(@Named("port") int port, @Named("address") String address) throws Exception {
        server = new Server(new InetSocketAddress(InetAddress.getByName(address), port));
        logger.at(Level.INFO).log("Jetty server configured on port: %s address: %s", port, address);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, CONTEXT_ROOT, SESSIONS);
        configureRestEasy(servletContextHandler);
        servletContextHandler.addEventListener(new GuiceResteasyBootstrapServletContextListener());
        servletContextHandler.addFilter(GuiceFilter.class, PATH_ALL, EnumSet.of(DispatcherType.REQUEST));
        servletContextHandler.setInitParameter("resteasy.guice.modules", "com.jkurapati.grocery.api.server.RestEasyModule");

        // You MUST add DefaultServlet or your server will always return 404s
        servletContextHandler.addServlet(DefaultServlet.class, "/");
    }

    private void configureRestEasy(ServletContextHandler servletContextHandler) {
        // Setup RESTEasy's HttpServletDispatcher at "/api/*".
        final ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter("resteasy.servlet.mapping.prefix",
                APPLICATION_PATH);
        servletContextHandler.addServlet(restEasyServlet, APPLICATION_PATH + PATH_ALL);
    }

    @Override
    public Server get() {
        return server;
    }
}
