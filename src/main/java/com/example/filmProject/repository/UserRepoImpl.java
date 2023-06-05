package com.example.filmProject.repository;


import com.example.filmProject.model.Employee;
import com.example.filmProject.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserRepoImpl implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {

        String query = "SELECT * FROM users";
        List<Object[]> resultList = em.createNativeQuery(query).getResultList();

        List<User> users = new ArrayList<>();
        for (Object[] row : resultList) {
            User user = new User();
            user.setId((Integer) row[0]);
            user.setName((String) row[1]);
            user.setEmail((String) row[2]);
            user.setPassword((String) row[3]);
            user.setInterestedIn((Integer) row[4]);
            user.setWatched((Integer) row[5]);

            users.add(user);
        }

        return users;
    }

    @Override
    public User getUserById(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        User user;
        try{
            // Get Employee with correct information
            String queryString = "SELECT e FROM User e WHERE e.email = :email AND e.password = :password";
            TypedQuery<User> query = em.createQuery(queryString, User.class);
            query.setParameter("email", username);
            query.setParameter("password", password);
            user = query.getSingleResult();
            return user;
        }catch (NoResultException e){
            return null;
        }

    }

    @Override
    public Void addUser(User user) {
        em.persist(user);
        return null;
    }

    @Override
    public Void deleteUser(int id) {
        em.remove(getUserById(id));
        return null;
    }

    @Override
    public User updateUser(User user) {
        em.merge(user);
        em.flush();
        return user;
    }
}