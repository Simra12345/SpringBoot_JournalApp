package com.simran.journalApp.controller;

import com.simran.journalApp.entities.User;
import com.simran.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id) {
        Optional<User> journal = userService.getUserById(id);
        return journal.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> removeJournalById(@PathVariable ObjectId id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            existingUser.setUsername(!updatedUser.getUsername().isEmpty() ? updatedUser.getUsername() : existingUser.getUsername());
            existingUser.setName(!updatedUser.getName().isEmpty() ? updatedUser.getName() : existingUser.getName());
            existingUser.setPassword(!updatedUser.getPassword().isEmpty() ? updatedUser.getPassword() : existingUser.getPassword());
            userService.addUser(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
