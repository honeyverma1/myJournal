package JournalApp.myJournal.reposiory;


import JournalApp.myJournal.entity.JournalEntry;
import JournalApp.myJournal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);

    void deleteByUsername(String username);
}
