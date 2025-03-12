package org.cwt.task.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.cwt.task.dto.BookRentDto;
import org.cwt.task.service.RentService;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.cwt.task.utils.DateConverter.parseDate;

@Path("/rent")
public class RentResources {
    @Inject
    private RentService rentService;

    @Inject
    private ModelMapper modelMapper;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookRentDto> getAllRents() {
        return rentService.getRentList()
                .stream()
                .map(x -> modelMapper.map(x, BookRentDto.class))
                .collect(Collectors.toList());
    }

    @POST
    public BookRentDto createRent(@QueryParam("rentDate") String rentDate,
                             @QueryParam("bookId") Long bookId,
                             @QueryParam("userId") UUID userId) {
        LocalDateTime rent = parseDate(rentDate);
        return modelMapper.map(rentService.takeRent(rent, bookId, userId), BookRentDto.class);
    }

    @PUT
    @Path("{id}")
    public void finalRent(@PathParam("id") UUID id) {
        rentService.returnRent(id);
    }

    @GET
    @Path("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookRentDto> userRents(@QueryParam("userId") UUID id) {
        return rentService.getRentList(id).stream().map(
                x -> modelMapper.map(x, BookRentDto.class))
                .collect(Collectors.toList());
    }
}
