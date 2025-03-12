package org.cwt.task.service.impl;

import org.cwt.task.model.entity.BookRent;
import org.cwt.task.model.entity.User;
import org.cwt.task.repository.BookRentRepository;
import org.cwt.task.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentServiceImplTest {

    @Mock
    private BookRentRepository bookRentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RentServiceImpl rentService;

    private User testUser;
    private BookRent testRent;
    private UUID testUserId;
    private Long testBookId;

    @BeforeEach
    public void setUp() {
        testUserId = UUID.randomUUID();
        testBookId = 1L;

        testUser = new User();
        testUser.setId(testUserId);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");

        testRent = new BookRent();
        testRent.setRentStatus(BookRent.RentStatus.OPENED);
        testRent.setUser(testUser);
        testRent.setRentDate(LocalDateTime.now());
    }

    @Test
    public void testTakeRent() {
        when(userService.getUser(testUserId)).thenReturn(testUser);
        when(bookRentRepository.takeRent(any(BookRent.class), eq(testBookId))).thenReturn(testRent);

        BookRent result = rentService.takeRent(LocalDateTime.now(), testBookId, testUserId);

        assertNotNull(result);
        assertEquals(BookRent.RentStatus.OPENED, result.getRentStatus());
        assertEquals(testUser, result.getUser());
        verify(bookRentRepository, times(1)).takeRent(any(BookRent.class), eq(testBookId));
    }

    @Test
    public void testReturnRent() {
        UUID rentId = UUID.randomUUID();

        rentService.returnRent(rentId);

        verify(bookRentRepository, times(1)).finalRent(rentId);
    }

    @Test
    public void testGetRentList() {
        when(bookRentRepository.findAll()).thenReturn(Arrays.asList(testRent));

        List<BookRent> result = rentService.getRentList();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testRent, result.get(0));
    }

    @Test
    public void testGetRentListByUserId() {
        when(bookRentRepository.findByUserId(testUserId)).thenReturn(Arrays.asList(testRent));

        List<BookRent> result = rentService.getRentList(testUserId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testRent, result.get(0));
    }
}
