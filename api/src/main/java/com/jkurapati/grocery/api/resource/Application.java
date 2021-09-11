package com.jkurapati.grocery.api.resource;

import java.util.HashSet;
import java.util.Set;

public class Application extends javax.ws.rs.core.Application {
    public Application() {

    }

    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> set = new HashSet<Object>();
        set.add(new VersionResource());
        return set;
    }
}
