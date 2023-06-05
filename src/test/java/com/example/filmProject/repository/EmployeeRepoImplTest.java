package com.example.filmProject.repository;

import com.example.filmProject.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // For JUnit 5
@SpringBootTest
class EmployeeRepoImplTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void getAllEmployees() {

        List<Employee> employees = employeeRepository.getAllEmployees();

        //This returns only the number of users that don't have a password
        assertEquals(4, employees.size());

    }

    @Test
    void getAllEmployeesCorrectness() {

        List<Employee> employees = employeeRepository.getAllEmployees();
        Employee employee = employees.get(0);

        assertEquals("Minion", employee.getName());

    }

    @Test
    void getEmployeeByID() {

        Employee employee = employeeRepository.getEmployeeById(1);
        assertEquals("Mario" , employee.getName());
    }

    @Test
    void getEmployeeByUsernameAndPassword(){
        Employee employee = employeeRepository.getEmployeeByUsernameAndPassword("test@test", "password");
        assertEquals("Mario" , employee.getName());
    }
}