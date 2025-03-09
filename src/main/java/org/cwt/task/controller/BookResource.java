package org.cwt.task.controller;

import org.cwt.task.entity.Book;
import org.cwt.task.repository.BookRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/book")
public class BookResource {
    @Inject
    private BookRepository bookRepository;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> hello() {
        return bookRepository.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book saveBook(Book book) {

        return bookRepository.save(book);
    }
}
