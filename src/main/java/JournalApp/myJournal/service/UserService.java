package JournalApp.myJournal.service;

import JournalApp.myJournal.entity.User;
import JournalApp.myJournal.reposiory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        }  catch (Exception e) {
            log.warn("User Already Exixts : {}", user.getUsername(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exixts");
        }
    }


    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public void deleteByUserName(String username) {
        userRepository.deleteByUsername(username);
    }
}

//controller -> service -> repository
//controller calls the service. and the service calls the repository.
//and repository externds to MongoRepository which takes in an entity and an unique id type