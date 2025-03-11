package org.cwt.task.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.cwt.task.service.UserService;

import java.util.UUID;

@Path("/analytic")
public class AnalyticResource {
    @Inject
    private UserService userService;

    @GET
    @Path("user/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void getUserAnalytic(@PathParam("id") String id,
                        @QueryParam("bookId") UUID bookId
    ) {

    }
}
