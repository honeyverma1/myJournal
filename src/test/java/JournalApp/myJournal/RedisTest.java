package JournalApp.myJournal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("email", "honey@email.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a = 1;
    }

}
