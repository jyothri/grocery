package com.jkurapati.grocery.api.server;

import com.google.inject.AbstractModule;
import com.jkurapati.grocery.api.resource.VersionResource;

public class RestEasyModule extends AbstractModule {
    @Override
    public void configure() {
        bind(VersionResource.class);
    }
}
