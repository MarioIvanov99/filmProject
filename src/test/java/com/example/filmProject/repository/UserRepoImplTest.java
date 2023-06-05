package com.example.filmProject.repository;

import com.example.filmProject.model.Employee;
import com.example.filmProject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // For JUnit 5
@SpringBootTest
class UserRepoImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllUsers() {

        List<User> users = userRepository.getAllUsers();
        assertEquals(15, users.size());

    }

    @Test
    void getAllEmployeesCorrectness() {

        List<User> users = userRepository.getAllUsers();
        User user = users.get(0);

        assertEquals("Harry Potter", user.getName());

    }

    @Test
    void getUserByID() {

        User user = userRepository.getUserById(1);
        assertEquals("Harry Potter" , user.getName());
    }

    @Test
    void getUserByUsernameAndPassword(){
        User user = userRepository.getUserByUsernameAndPassword("test@test3", "password3");
        assertEquals("Luke Skywalker" , user.getName());
    }
}