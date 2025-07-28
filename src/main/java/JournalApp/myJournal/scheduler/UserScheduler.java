package JournalApp.myJournal.scheduler;

import JournalApp.myJournal.cache.AppCache;
import JournalApp.myJournal.entity.JournalEntry;
import JournalApp.myJournal.entity.User;
import JournalApp.myJournal.enums.Sentiment;
import JournalApp.myJournal.reposiory.UserRepositoryImpl;
import JournalApp.myJournal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Scheduled(cron = "0 0 9 ? * SUN")
    public void fetchUserAndSendSAMail() {
        List<User> userForSA = userRepository.getUserForSA();

        for(User user : userForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> collectedEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : collectedEntries) {
                if(sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFreq = null;
            int max = 0;
            for(Sentiment s : sentimentCounts.keySet()) {
                if(sentimentCounts.get(s) > max) {
                    max = sentimentCounts.get(s);
                    mostFreq = s;
                }
            }

            if(mostFreq != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFreq.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * 1/1 * ?")
    public void refreshCache(){
        appCache.innit();
    }
}
