package org.cwt.task.controller;

import org.cwt.task.dto.BookRentDto;
import org.cwt.task.entity.BookRent;
import org.cwt.task.service.RentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/rent")
public class RentResources {
    @Inject
    private RentService rentService;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookRentDto> bookRents(@QueryParam("bookId") Long bookId) {
        return rentService.getRentList();
    }
}
