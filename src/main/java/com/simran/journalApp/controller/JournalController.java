package com.simran.journalApp.controller;


import com.simran.journalApp.entities.Journal;
import com.simran.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalController {

    @Autowired
    private JournalService journalService;


    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalsForUser(@PathVariable String username) {

        List<Journal> journals = journalService.getJournalsForUser(username);
        if (journals != null && !journals.isEmpty()) {
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id) {
        Optional<Journal> journal = journalService.getJournalById(id);
        return journal.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{username}/{id}")
    public ResponseEntity<?> removeJournalById(@PathVariable ObjectId id, @PathVariable String username) {
        journalService.removeJournalById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addJournal(@RequestBody Journal entry, @PathVariable String username) {
        try {
            journalService.addJournalForUser(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id, @RequestBody Journal updatedEntry) {
        journalService.getJournalById(id).map(existingEntry -> {
            existingEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty() ? updatedEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty() ? updatedEntry.getContent() : existingEntry.getContent());
            journalService.addJournal(existingEntry);
            return new ResponseEntity<>(existingEntry, HttpStatus.OK);
        });
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
