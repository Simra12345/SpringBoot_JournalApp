package com.simran.journalApp.controller;

import com.simran.journalApp.entity.Journal;
import com.simran.journalApp.entity.User;
import com.simran.journalApp.services.JournalService;
import com.simran.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllJournals() {
        List<Journal> all = journalService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @GetMapping("/my")
    public ResponseEntity<?> getMyJournalEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<Journal> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>("No journal entries found", HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody Journal myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            journalService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName, @PathVariable ObjectId id) {
        try {
            journalService.deleteById(id, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting journal entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  //  @PutMapping("/{userName}/{id}")
   // public ResponseEntity<?> updateJournalEntry(
         //   @PathVariable String userName,
         //   @PathVariable ObjectId id,
         //   @RequestBody Journal updatedEntry
 //   ) {
       // try {
          //  journalService.updateEntry(id, userName, updatedEntry);
          //  return new ResponseEntity<>("Journal updated successfully", HttpStatus.OK);
    //    } catch (Exception e) {
       //     return new ResponseEntity<>("Error updating journal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      //  }
  //  }
}





