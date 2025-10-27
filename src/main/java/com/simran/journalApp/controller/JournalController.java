package com.simran.journalApp.controller;

import com.simran.journalApp.entity.Journal;
import com.simran.journalApp.entity.User;
import com.simran.journalApp.repository.JournalEntryRepository;
import com.simran.journalApp.services.JournalService;
import com.simran.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;



    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<Journal> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public void removeJournalById(@PathVariable ObjectId id) {
        journalService.removeJournalById(id);
    }

    @PostMapping("{userName}")
    public ResponseEntity<Journal> createEntry(@RequestBody Journal myEntry, @PathVariable String userName){
        try {
            User user = userService.findByUserName(userName);
            journalService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{id}")
    public void updateJournal(@PathVariable ObjectId id, @RequestBody Journal updatedEntry) {
        journalService.getJournalById(id).map(existingEntry -> {
            existingEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty() ? updatedEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty() ? updatedEntry.getContent() : existingEntry.getContent());
            journalService.addJournal(existingEntry);
            return true;
        });
    }

}
