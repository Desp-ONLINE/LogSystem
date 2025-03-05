package LogSystem.logSystem.database.repository;

import LogSystem.logSystem.database.DatabaseRegister;
import LogSystem.logSystem.dto.UserLogDto;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;

public class UserLogRepository {

    private static MongoCollection<Document> userLog = null;
    private static final Map<String, UserLogDto> userLogMap = new HashMap<>();

    public UserLogRepository() {
        DatabaseRegister database = new DatabaseRegister();
        userLog = database.getDatabase().getCollection("UserLog");
    }

    public static boolean findUser(String uuid) {
        Document query = new Document("uuid", uuid);
        FindIterable<Document> iterable = userLog.find(query);
        if (iterable.first() == null) {
            return false;
        }
        return true;
    }

    public Map<String, UserLogDto> getUserLog() {
        FindIterable<Document> documents = userLog.find();

        for (Document document : documents) {
            String uuid = document.getString("uuid");
            String user_id = document.getString("user_id");

            UserLogDto userLogDto = UserLogDto.builder()
                    .user_id(user_id)
                    .uuid(uuid)
                    .firstJoin(document.getString("firstJoin"))
                    .lastConnection(document.getString("lastConnection"))
                    .build();

            userLogMap.put(uuid, userLogDto);
        }
        return userLogMap;
    }

    public static void insertUserLog(UserLogDto userLogDto) {
        Document document = new Document()
                .append("user_id", userLogDto.getUser_id())
                .append("uuid", userLogDto.getUuid())
                .append("firstJoin", userLogDto.getFirstJoin())
                .append("lastConnection", null);

        userLog.insertOne(document);
    }

    public static void updateUserLog(String uuid, String lastConnection) {
        Document filter = new Document("uuid", uuid);
        Document update = new Document("$set", new Document("lastConnection", lastConnection));

        userLog.updateOne(filter, update);
    }
}
