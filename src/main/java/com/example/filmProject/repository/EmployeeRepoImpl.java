package com.example.filmProject.repository;


import com.example.filmProject.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EmployeeRepoImpl implements EmployeeRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employee> getAllEmployees() {

        // Only employees that aren't admins
        String query = "SELECT * FROM employees WHERE password IS NULL";
        List<Object[]> resultList = em.createNativeQuery(query).getResultList();

        List<Employee> employees = new ArrayList<>();

        // Set all Employee attributes
        for (Object[] row : resultList) {
            Employee employee = new Employee();
            employee.setId((Integer) row[0]);
            employee.setName((String) row[1]);
            employee.setEmail((String) row[2]);
            employee.setPassword((String) row[3]);

            employees.add(employee);
        }

        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return em.find(Employee.class, id);
    }

    @Override
    public Employee getEmployeeByUsernameAndPassword(String username, String password) {

        Employee employee;

        try{

            // Get Employee with correct information
            String queryString = "SELECT e FROM Employee e WHERE e.email = :email AND e.password = :password";
            TypedQuery<Employee> query = em.createQuery(queryString, Employee.class);
            query.setParameter("email", username);
            query.setParameter("password", password);
            employee = query.getSingleResult();
            return employee;

        }catch (NoResultException e){
            return null;
        }

    }

    @Override
    public Void addEmployee(Employee employee) {
        em.persist(employee);
        return null;
    }

    @Override
    public Void deleteEmployee(int id) {
        em.remove(getEmployeeById(id));
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        em.merge(employee);
        em.flush();
        return employee;
    }
}