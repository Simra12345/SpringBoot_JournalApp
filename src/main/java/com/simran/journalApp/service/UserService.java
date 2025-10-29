package com.simran.journalApp.service;

import com.simran.journalApp.entities.User;
import com.simran.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository repository;

    public void addUser(User user) {
        repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return repository.findById(id);
    }

    public void removeUserById(ObjectId id) {
        repository.deleteById(id);
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username);
    }
}
