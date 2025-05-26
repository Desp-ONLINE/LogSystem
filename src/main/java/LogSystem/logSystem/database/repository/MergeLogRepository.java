package LogSystem.logSystem.database.repository;

import LogSystem.logSystem.database.DatabaseRegister;
import LogSystem.logSystem.dto.MergeLogDto;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bukkit.entity.Player;

public class MergeLogRepository {

    private static MongoCollection<Document> mergeLog = null;
    private static final Map<String, MergeLogDto> mergeLogMap = new HashMap<>();

    public MergeLogRepository() {
        DatabaseRegister database = new DatabaseRegister();
        mergeLog = database.getDatabase().getCollection("MergeLog");
    }

    public Map<String, MergeLogDto> getMergeLog() {
        FindIterable<Document> documents = mergeLog.find();

        for (Document document : documents) {
            String user_id = document.getString("user_id");
            String uuid = document.getString("uuid");

            MergeLogDto mergeLogDto = MergeLogDto.builder()
                    .user_id(user_id)
                    .uuid(uuid)
                    .target_item_id(document.getString("target_item_id"))
                    .success(document.getBoolean("success"))
                    .used_materials(document.get("used_materials", Map.class))
                    .build();

            mergeLogMap.put(uuid, mergeLogDto);
        }

        return mergeLogMap;
    }

    public static void insertMergeLog(MergeLogDto mergeLogDto, String currentTime) {
        Document document = new Document()
                .append("user_id", mergeLogDto.getUser_id())
                .append("uuid", mergeLogDto.getUuid())
                .append("target_item_id", mergeLogDto.getTarget_item_id())
                .append("success", mergeLogDto.isSuccess())
                .append("used_materials", mergeLogDto.getUsed_materials())
                .append("created_at", currentTime);

        mergeLog.insertOne(document);
    }

    public static boolean isPlayerFirstMerge(Player player) {
        String uuid = player.getUniqueId().toString();

        Document document = mergeLog.find(Filters.eq("uuid", uuid)).first();

        return document == null;
    }
}
