package JournalApp.myJournal;

import JournalApp.myJournal.reposiory.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@Disabled
public class JournalEntryServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName() {
        assertEquals("Not Equal", 4, 5);
        assertNotNull("Null Test",userRepository.findByUsername("ram"));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "10, 2, 12",
            "3, 3, 9"
    })
    public void test(int a, int b, int exp){
        assertEquals("Val eq", exp, a + b);
    }
}
