package org.cwt.task.repository;

import org.cwt.task.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User findById(UUID id);

    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void deleteById(UUID id);
}
