package JournalApp.myJournal.entity;

import JournalApp.myJournal.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//if you don't pass anything in the document annotation. it will look for a collection named after the class.
//if not found, will create a collection.
@Data
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

}
