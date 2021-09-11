package com.jkurapati.grocery.api.resource;

import com.jkurapati.grocery.api.model.Version;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/version")
@Produces(MediaType.APPLICATION_JSON)
public class VersionResource {
    @GET
    public Version getVersion() {
        Version version = new Version();
        version.setVersion("0.01");
        return version;
    }
}
