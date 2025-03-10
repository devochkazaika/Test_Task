package org.cwt.task.service;

import org.cwt.task.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User addUser(User user);

    User getUser(UUID id);

    List<User> getUsers();

    void deleteUser(UUID id);

}
