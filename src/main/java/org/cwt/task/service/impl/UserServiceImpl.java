package org.cwt.task.service.impl;

import jakarta.inject.Inject;
import org.cwt.task.entity.User;
import org.cwt.task.repository.UserRepository;
import org.cwt.task.service.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Inject
    private UserRepository userRepository;

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
