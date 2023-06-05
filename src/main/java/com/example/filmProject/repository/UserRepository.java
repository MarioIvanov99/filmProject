package com.example.filmProject.repository;

import com.example.filmProject.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByUsernameAndPassword(String username, String password);
    Void addUser(User user);
    Void deleteUser(int id);
    User updateUser(User user);
}
