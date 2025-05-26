package LogSystem.logSystem.database.repository;

import LogSystem.logSystem.database.DatabaseRegister;
import LogSystem.logSystem.dto.MonsterKillLogDto;
import LogSystem.logSystem.listener.MonsterListener;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import org.bson.Document;

import java.util.Map;

public class MonsterKillLogRepository {

    private static MongoCollection<Document> monsterKillLog = null;
    private static Map<String, MonsterKillLogDto> monsterKillLogMap = new HashMap<>();

    public MonsterKillLogRepository() {
        DatabaseRegister database = new DatabaseRegister();
        monsterKillLog = database.getDatabase().getCollection("MonsterKillLog");
    }

    public Map<String, MonsterKillLogDto> loadMonsterKillLog() {
        FindIterable<Document> documents = monsterKillLog.find();

        for (Document document : documents) {
            String user_id = document.getString("user_id");
            String uuid = document.getString("uuid");
            Integer kills = document.getInteger("kills");

            MonsterKillLogDto monsterKillLogDto = MonsterKillLogDto.builder()
                    .user_id(user_id)
                    .uuid(uuid)
                    .kills(kills)
                    .build();

            monsterKillLogMap.put(uuid, monsterKillLogDto);

        }
        return monsterKillLogMap;
    }

    public static void updateMonsterKillLog(MonsterKillLogDto monsterKillLogDto, String currentTime) {

        Document document = new Document()
                .append("user_id", monsterKillLogDto.getUser_id())
                .append("uuid", monsterKillLogDto.getUuid())
                .append("kills", monsterKillLogDto.getKills())
                .append("updated_at", currentTime);

        monsterKillLog.insertOne(document);
    }
}
