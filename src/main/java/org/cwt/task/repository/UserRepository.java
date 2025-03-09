package org.cwt.task.repository;

import org.cwt.task.entity.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

}
