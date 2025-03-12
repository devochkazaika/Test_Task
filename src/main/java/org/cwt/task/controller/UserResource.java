package org.cwt.task.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.cwt.task.dto.UserDto;
import org.cwt.task.model.entity.User;
import org.cwt.task.service.UserService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
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
