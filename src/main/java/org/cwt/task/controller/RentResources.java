package org.cwt.task.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.cwt.task.dto.BookRentDto;
import org.cwt.task.entity.BookRent;
import org.cwt.task.service.RentService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/rent")
public class RentResources {
    @Inject
    private RentService rentService;

    @Inject
    private ModelMapper modelMapper;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookRentDto> getRents(@QueryParam("bookId") Long bookId) {
        return rentService.getRentList()
                .stream()
                .map(x -> modelMapper.map(x, BookRentDto.class))
                .collect(Collectors.toList());
    }

    @POST
    public BookRent bookRent(BookRentDto bookRent,
                             @QueryParam("bookId") Long bookId,
                             @QueryParam("userId") UUID userId) {
        return rentService.takeRent(bookRent, bookId, userId);
    };

    @PUT()
    @Path("{id}")
    public void backRent(@PathParam("id") UUID id) {
        rentService.returnRent(id);
    }

    @GET
    @Path("user/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookRentDto> userRents(@PathParam("id") UUID id) {
        return rentService.getRentList();
    }
}
