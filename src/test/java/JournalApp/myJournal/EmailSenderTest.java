package JournalApp.myJournal;

import JournalApp.myJournal.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testMailSender(){
        emailService.sendEmail("email@gmail.com", "Testing Java Email Sender", "yessir! it worked just fine.");
    }
}
