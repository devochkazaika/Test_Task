package org.cwt.task.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.cwt.task.dto.UserDto;
import org.cwt.task.entity.User;
import org.cwt.task.service.UserService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Path("/user")
public class UserResource {
    @Inject
    private UserService userService;

    @Inject
    private ModelMapper modelMapper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(UserDto user) {
        return userService.addUser(modelMapper.map(user, User.class));
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Path("{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") UUID id) {
        return userService.getUser(id);
    }

    @Path("{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("id") UUID id) {
        userService.deleteUser(id);
    }
}
