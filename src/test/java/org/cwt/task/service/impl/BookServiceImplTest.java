package org.cwt.task.service.impl;

import org.cwt.task.exception.NotFoundException;
import org.cwt.task.model.entity.Book;
import org.cwt.task.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setName("Test Book");
        testBook.setAuthor("Test Author");

    }

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook));

        var result = bookService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getName());
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(testBook);

        var result = bookService.getById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getName());
    }

    @Test
    public void testSaveBook() {
        when(bookRepository.save(testBook)).thenReturn(testBook);

        var result = bookService.save(testBook);

        assertNotNull(result);
        assertEquals("Test Book", result.getName());
    }

    @Test
    public void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setName("Updated Book");
        updatedBook.setAuthor("Updated Author");

        when(bookRepository.findById(1L)).thenReturn(testBook);
        when(bookRepository.save(testBook)).thenReturn(testBook);

        bookService.update(1L, updatedBook);
        verify(bookMapper, times(1)).map(updatedBook, testBook);
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetBookByIdNotFound() {
        when(bookRepository.findById(2L)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> bookService.getById(2L));
    }
}
