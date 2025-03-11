package org.cwt.task.controller;

import org.cwt.task.dto.BookDto;
import org.cwt.task.entity.Book;
import org.cwt.task.service.BookService;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/book")
public class BookResource {
    @Inject
    private BookService bookService;

    @Inject
    private ModelMapper modelMapper;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book saveBook(@Valid BookDto book) {
        Book bookEntity = modelMapper.map(book, Book.class);
        return bookService.save(bookEntity);
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteBook(@PathParam("id") Long id) {
        bookService.deleteById(id);
    }

    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book updateBook(@PathParam("id") Long id, BookDto book) {
        return bookService.update(id, modelMapper.map(book, Book.class));
    }
}
