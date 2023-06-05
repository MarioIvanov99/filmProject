package com.example.filmProject.repository;

import com.example.filmProject.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    Employee getEmployeeByUsernameAndPassword(String username, String password);
    Void addEmployee(Employee cart);
    Void deleteEmployee(int id);
    Employee updateEmployee(Employee cart);
}
