package com.simran.journalApp.services;


import com.simran.journalApp.entity.Journal;
import com.simran.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.simran.journalApp.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private com.simran.journalApp.repository.JournalEntryRepository repository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;

    @Transactional
    public void saveEntry(Journal journal, String userName) {
        User user =userService.findByUserName(userName);
        journal.setDate(LocalDateTime.now());
        Journal saved = journalEntryRepository.save(journal);
        user.getJournalEntries().add(saved);
       // user.setUserName(null);
        userService.saveEntry(user);
    }

    public List<Journal> getJournals() {
        return repository.findAll();
    }
    public List<Journal> getAll() {
        return journalEntryRepository.findAll();
    }


    public Optional<Journal> getJournalById(ObjectId id) {
        return repository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }

   // public void saveEntry(Journal journal, String userName) {
       // journalEntryRepository.save(journal);
    }

