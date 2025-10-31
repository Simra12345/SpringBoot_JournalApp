package com.simran.journalApp.services;

import com.simran.journalApp.entity.User;
import com.simran.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();

    }

    public Optional<User> findById(ObjectId id) {

        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {

        userRepository.deleteById(id);
    }


    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);

    }

    public void deleteUser(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public void saveNewUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

      if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("USER"));
        }

        userRepository.save(user);
    }


}
