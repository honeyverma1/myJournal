package JournalApp.myJournal.controller;

import JournalApp.myJournal.entity.JournalEntry;
import JournalApp.myJournal.entity.User;
import JournalApp.myJournal.service.JournalEntryService;
import JournalApp.myJournal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

        @GetMapping("/{username}")
        public ResponseEntity<?> getEntriesByUser(@PathVariable String username) {
            User user = userService.findByUserName(username);
            List<JournalEntry> all = user.getJournalEntries();
            if(all != null && !all.isEmpty()){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @PostMapping("/{username}")
        public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username) {
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @GetMapping("/id/{myID}")
        public ResponseEntity<JournalEntry> findViaId(@PathVariable ObjectId myID){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myID);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/id/{username}/{myId}")
        public ResponseEntity<JournalEntry> deleteEntry(@PathVariable String username, @PathVariable ObjectId myId) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                journalEntryService.deleteById(myId, username);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PutMapping("id/{myId}")
        public ResponseEntity<JournalEntry> updateEntry( @PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
            JournalEntry old = journalEntryService.findById(myId).orElse(null);
            if(old != null){
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                //journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK );
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


}
