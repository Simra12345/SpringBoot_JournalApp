package com.simran.journalApp.services;


import com.simran.journalApp.entity.Journal;
import com.simran.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private com.simran.journalApp.repository.JournalEntryRepository repository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void addJournal(Journal entry) {
        entry.setDate(LocalDateTime.now());
        repository.save(entry);
    }

    public List<Journal> getJournals() {
        return repository.findAll();
    }

    public Optional<Journal> getJournalById(ObjectId id) {
        return repository.findById(id);
    }

    public void removeJournalById(ObjectId id) {
        repository.deleteById(id);
    }

    public void saveEntry(Journal journal) {
        journalEntryRepository.save(journal);
    }
}
