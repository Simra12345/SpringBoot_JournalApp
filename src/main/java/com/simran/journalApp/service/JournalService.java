package com.simran.journalApp.service;

import com.simran.journalApp.entities.Journal;
import com.simran.journalApp.entities.User;
import com.simran.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private JournalRepository repository;

    @Autowired
    private UserService userService;

    @Transactional
    public void addJournalForUser(Journal entry, String username) {
        User user = userService.getUserByUsername(username);
        user.getJournals().add(addJournal(entry));
        userService.addUser(user);
    }

    public Journal addJournal(Journal entry) {
        entry.setDate(LocalDateTime.now());
        return repository.save(entry);
    }

    public List<Journal> getJournalsForUser(String username) {
        User user = userService.getUserByUsername(username);
        return user.getJournals();
    }

    public Optional<Journal> getJournalById(ObjectId id) {
        return repository.findById(id);
    }

    public void removeJournalById(ObjectId id, String username) {
        User user = userService.getUserByUsername(username);
        user.getJournals().removeIf(journal -> journal.getId().equals(id));
        userService.addUser(user);
        repository.deleteById(id);
    }
}
