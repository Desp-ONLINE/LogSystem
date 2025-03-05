package LogSystem.logSystem.database.repository;

import LogSystem.logSystem.database.DatabaseRegister;
import LogSystem.logSystem.dto.UpgradeDto;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;

public class UpgradeLogRepository {

    private static MongoCollection<Document> upgradeLog = null;
    private static final Map<String, UpgradeDto> upgradeLogMap = new HashMap<>();

    public UpgradeLogRepository() {
        DatabaseRegister database = new DatabaseRegister();
        upgradeLog = database.getDatabase().getCollection("UpgradeLog");
    }

    public Map<String, UpgradeDto> getUpgradeLog() {
        FindIterable<Document> documents = upgradeLog.find();

        for (Document document : documents) {
            String user_id = document.getString("user_id");
            String uuid = document.getString("uuid");
            UpgradeDto upgradeDto = UpgradeDto.builder()
                    .user_id(user_id)
                    .uuid(uuid)
                    .item_id(document.getString("item_id"))
                    .success(document.getBoolean("success"))
                    .destroy(document.getBoolean("destroy"))
                    .used_materials(document.get("used_materials", Map.class))
                    .build();

            upgradeLogMap.put(uuid, upgradeDto);
        }
        return upgradeLogMap;
    }

    public static void insertUpgradeLog(UpgradeDto upgradeDto, String currentTime) {
        Document document = new Document()
                .append("user_id", upgradeDto.getUser_id())
                .append("uuid", upgradeDto.getUuid())
                .append("item_id", upgradeDto.getItem_id())
                .append("success", upgradeDto.isSuccess())
                .append("destroy", upgradeDto.isDestroy())
                .append("used_materials", upgradeDto.getUsed_materials())
                .append("created_at", currentTime);

        upgradeLog.insertOne(document);
    }
}
