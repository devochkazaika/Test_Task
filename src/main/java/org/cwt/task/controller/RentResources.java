package org.cwt.task.controller;

import org.cwt.task.dto.BookRentDto;
import org.cwt.task.entity.BookRent;
import org.cwt.task.service.RentService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
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
}
