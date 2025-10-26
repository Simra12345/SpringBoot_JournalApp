package com.simran.journalApp.controller;

import com.simran.journalApp.entity.Journal;
import com.simran.journalApp.services.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalController {

    @Autowired
    private JournalService journalService;


    @GetMapping
    public List<Journal> getAllEntries() {
        return journalService.getJournals();
    }

    @GetMapping("id/{id}")
    public Journal getJournalById(@PathVariable ObjectId id) {
        return journalService.getJournalById(id).orElse(null);
    }

    @DeleteMapping("id/{id}")
    public void removeJournalById(@PathVariable ObjectId id) {
        journalService.removeJournalById(id);
    }

    @PostMapping
    public void addJournal(@RequestBody Journal entry) {
        journalService.addJournal(entry);
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
