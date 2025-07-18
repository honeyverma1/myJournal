package JournalApp.myJournal.service;

import JournalApp.myJournal.entity.User;
import JournalApp.myJournal.reposiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
       if(user != null) {
           return org.springframework.security.core.userdetails.User.builder()
                   .username(user.getUsername())
                   .password(user.getPassword())
                   .roles(user.getRoles().toArray(new String[0]))
                   .build();
       } else {
           throw new UsernameNotFoundException("No User found with username = " + username);
       }
    }
}
