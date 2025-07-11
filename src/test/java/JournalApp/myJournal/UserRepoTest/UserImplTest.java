package JournalApp.myJournal.UserRepoTest;

import JournalApp.myJournal.reposiory.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class UserImplTest {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Test
    public void getUserTest(){
        userRepositoryImpl.getUserForSA();
    }


}
