package org.cwt.task.controller;

import org.cwt.task.entity.BookRent;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/rent")
public class RentResources {
    @POST
    public BookRent bookRent(BookRent bookRent) {
        return bookRent;
    };
}
