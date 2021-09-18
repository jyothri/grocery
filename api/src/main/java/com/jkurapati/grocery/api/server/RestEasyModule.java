package com.jkurapati.grocery.api.server;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.jkurapati.grocery.api.resource.VersionResource;

public class RestEasyModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(VersionResource.class);
    }
}
