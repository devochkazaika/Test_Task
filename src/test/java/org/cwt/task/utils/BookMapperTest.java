package org.cwt.task.utils;

import junit.framework.TestCase;
import org.cwt.task.dto.BookRentDto;
import org.cwt.task.model.entity.Book;
import org.cwt.task.model.entity.BookRent;
import org.cwt.task.model.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class BookMapperTest extends TestCase {
    private BookMapper bookMapper;

    @BeforeEach
    public void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    public void testMappingBookRentToBookRentDto() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("testName");
        user.setLastName("testLastName");
        Book book = new Book(1L,"Book Title", "Author Name", LocalDate.now(), 2, Book.BookTheme.FANTASTIC);
        BookRent bookRent = new BookRent();
        bookRent.setId(UUID.randomUUID());
        bookRent.setRentDate(LocalDateTime.now());
        bookRent.setReturnDate(LocalDateTime.now().plusDays(7));
        bookRent.setUser(user);
        bookRent.setBook(book);
        bookRent.setRentStatus(BookRent.RentStatus.OPENED);

        BookRentDto bookRentDto = bookMapper.map(bookRent, BookRentDto.class);

        assertNotNull(bookRentDto);
        Assertions.assertAll(
                () -> assertEquals(bookRent.getRentDate(), bookRentDto.getRentDate()),
                () -> assertEquals(bookRent.getReturnDate(), bookRentDto.getReturnDate()),
                () -> assertEquals(bookRent.getUser().getFirstName(), bookRentDto.getFirstName()),
                () -> assertEquals(bookRent.getUser().getLastName(), bookRentDto.getLastName()),
                () -> assertEquals(bookRent.getBook().getName(), bookRentDto.getBookName())
        );
        Assertions.assertAll(
                () -> assertEquals(user.getFirstName(), bookRentDto.getFirstName()),
                () -> assertEquals(user.getLastName(), bookRentDto.getLastName())
        );

    }

    @Test
    public void testMappingNullFields() {
        BookRent bookRent = new BookRent();
        bookRent.setUser(null);
        bookRent.setBook(null);

        BookRentDto bookRentDto = bookMapper.map(bookRent, BookRentDto.class);

        assertNull(bookRentDto.getFirstName());
        assertNull(bookRentDto.getLastName());
        assertNull(bookRentDto.getBookName());
    }

    @Test
    public void testMappingEmptyBookRent() {
        BookRent bookRent = new BookRent();

        BookRentDto bookRentDto = bookMapper.map(bookRent, BookRentDto.class);

        assertNotNull(bookRentDto);
    }

    @Test
    public void testMappingBookRentDtoToBookRent() {
        // Создание тестового объекта BookRentDto
        BookRentDto bookRentDto = new BookRentDto();
        bookRentDto.setRentDate(LocalDateTime.now());
        bookRentDto.setReturnDate(LocalDateTime.now().plusDays(7));
        bookRentDto.setFirstName("testName");
        bookRentDto.setLastName("testLastName");
        bookRentDto.setBookName("Book Title");

        BookRent bookRent = bookMapper.map(bookRentDto, BookRent.class);

        Assertions.assertAll(
                () -> assertNotNull(bookRent),
                () -> assertEquals(bookRentDto.getRentDate(), bookRent.getRentDate()),
                () -> assertEquals(bookRentDto.getReturnDate(), bookRent.getReturnDate())
        );

        User user = new User();
        user.setFirstName(bookRentDto.getFirstName());
        user.setLastName(bookRentDto.getLastName());
        bookRent.setUser(user);

        Book book = new Book(1L, bookRentDto.getBookName(), "Author Name", LocalDate.now(), 2, Book.BookTheme.FANTASTIC);
        bookRent.setBook(book);

        Assertions.assertAll(
                () -> assertEquals(bookRentDto.getFirstName(), bookRent.getUser().getFirstName()),
                () -> assertEquals(bookRentDto.getLastName(), bookRent.getUser().getLastName()),
                () -> assertEquals(bookRentDto.getBookName(), bookRent.getBook().getName())
        );
    }

    @Test
    public void testMappingNullFieldsInDto() {
        BookRentDto bookRentDto = new BookRentDto();
        bookRentDto.setFirstName(null);
        bookRentDto.setLastName(null);
        bookRentDto.setBookName(null);

        BookRent bookRent = bookMapper.map(bookRentDto, BookRent.class);

        Assertions.assertAll(
                () -> assertNull(bookRent.getUser()),
                () -> assertNull(bookRent.getBook())
        );
    }

    @Test
    public void testMappingEmptyBookRentDto() {
        BookRentDto bookRentDto = new BookRentDto();

        BookRent bookRent = bookMapper.map(bookRentDto, BookRent.class);

        assertNotNull(bookRent);
    }
}