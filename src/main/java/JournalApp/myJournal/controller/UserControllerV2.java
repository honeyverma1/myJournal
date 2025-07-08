package JournalApp.myJournal.controller;

import JournalApp.myJournal.api.response.WeatherResponse;
import JournalApp.myJournal.entity.JournalEntry;
import JournalApp.myJournal.entity.User;
import JournalApp.myJournal.service.UserService;
import JournalApp.myJournal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserControllerV2 {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @DeleteMapping
    public ResponseEntity<JournalEntry> deleteEntry() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @GetMapping
    public ResponseEntity<?> greetingsUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse response = weatherService.getWeather("Noida");
        String greetings = "";
        if(response != null) {
            greetings = " , Weather feels like " + response.getCurrent().getFeelslike() + " °C";
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greetings, HttpStatus.OK);
    }
}
