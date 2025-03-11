package org.cwt.task.service.impl;

import org.cwt.task.model.entity.User;
import org.cwt.task.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(userId);
        testUser.setFirstName("Test");
        testUser.setLastName("User");
    }

    @Test
    public void testGetUser() {
        when(userRepository.findById(userId)).thenReturn(testUser);

        User result = userService.getUser(userId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testUser.getId(), result.getId());
        Assertions.assertEquals(testUser.getFirstName(), result.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), result.getLastName());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testAddUser() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User result = userService.addUser(testUser);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testUser.getId(), result.getId());

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testGetUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<User> result = userService.getUsers();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testUser.getId(), result.get(0).getId());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
