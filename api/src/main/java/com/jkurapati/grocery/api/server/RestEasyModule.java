package com.jkurapati.grocery.api.server;

import com.google.inject.AbstractModule;
import org.reflections.Reflections;

import javax.ws.rs.Path;
import java.util.Set;

public class RestEasyModule extends AbstractModule {

    private static final String API_RESOURCE_PACKAGE = "com.jkurapati.grocery.api.resource";

    @Override
    public void configure() {
        Reflections reflections = new Reflections(API_RESOURCE_PACKAGE);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Path.class);
        for (Class<?> clazz : annotated) {
            bind(clazz);
        }
    }
}
