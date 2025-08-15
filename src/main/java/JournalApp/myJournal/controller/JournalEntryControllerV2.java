package JournalApp.myJournal.controller;

import JournalApp.myJournal.entity.JournalEntry;
import JournalApp.myJournal.entity.User;
import JournalApp.myJournal.service.JournalEntryService;
import JournalApp.myJournal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

        @GetMapping("/display")
        public ResponseEntity<?> getEntriesByUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            List<JournalEntry> all = user.getJournalEntries();
            if(all != null && !all.isEmpty()){
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }



        @PostMapping("/create")
        public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                journalEntryService.saveEntry(myEntry, username);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/id/{myId}")
        public ResponseEntity<JournalEntry> findViaId(@PathVariable ObjectId myId){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
            if(!collect.isEmpty()) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if(journalEntry.isPresent()) return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/id/{myId}")
        public ResponseEntity<JournalEntry> deleteEntry(@PathVariable ObjectId myId) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();User user = userService.findByUserName(username);
            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
            if(!collect.isEmpty()){
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if(journalEntry.isPresent()) {
                    journalEntryService.deleteById(myId, username);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PutMapping("id/{myId}")
        public ResponseEntity<JournalEntry> updateEntry( @PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
            if(!collect.isEmpty()) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if(journalEntry.isPresent()) {
                    JournalEntry old = journalEntry.get();
                    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                    old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                    journalEntryService.saveEntry(old, username);
                    return new ResponseEntity<>(old, HttpStatus.OK );
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


}
