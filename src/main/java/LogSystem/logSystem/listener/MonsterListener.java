package LogSystem.logSystem.listener;

import LogSystem.logSystem.database.repository.MonsterKillLogRepository;
import LogSystem.logSystem.dto.MonsterKillLogDto;
import LogSystem.logSystem.utils.LogSystemUtils;
import io.lumine.mythic.lib.api.event.PlayerKillEntityEvent;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MonsterListener implements Listener {
    private final Map<String, Map<String, Integer>> playerKillCache = new HashMap<>();

    @EventHandler
    public void onMonsterKillEvent(PlayerKillEntityEvent event) {
        Player player = event.getPlayer();
        LivingEntity target = event.getTarget();
        String playerName = player.getName();
        String monsterName = target.getName();

        Map<String, Integer> killData = playerKillCache.computeIfAbsent(playerName, k -> new HashMap<>());

        killData.put(monsterName, killData.getOrDefault(monsterName, 0) + 1);

        int totalKills = killData.values().stream().mapToInt(Integer::intValue).sum();

        if (totalKills >= 50) {
            MonsterKillLogDto logDto = MonsterKillLogDto.builder()
                    .user_id(playerName)
                    .uuid(player.getUniqueId().toString())
                    .kills(killData)
                    .build();

            MonsterKillLogRepository.updateMonsterKillLog(logDto, LogSystemUtils.getCurrentTime());

            playerKillCache.put(playerName, new HashMap<>());
        }
    }
}
