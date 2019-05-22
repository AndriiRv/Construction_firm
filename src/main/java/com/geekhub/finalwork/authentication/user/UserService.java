package com.geekhub.finalwork.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public void updateUser(User user, String username) {
        userRepository.updateUser(user, username);
    }

    public void doUserIsAdmin(int id) {
        userRepository.doUserIsAdmin(id);
    }

    public void doUserIsUser(int id) {
        userRepository.doUserIsUser(id);
    }
}