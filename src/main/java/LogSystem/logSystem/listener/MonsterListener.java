package LogSystem.logSystem.listener;

import LogSystem.logSystem.database.repository.MonsterKillLogRepository;
import LogSystem.logSystem.dto.MonsterKillLogDto;
import LogSystem.logSystem.utils.LogSystemUtils;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.lib.api.event.PlayerKillEntityEvent;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MonsterListener implements Listener {
    private final Map<String, Integer> playerKillCache = new HashMap<>();

    @EventHandler
    public void onMonsterKillEvent(MythicMobDeathEvent event) {
        if (event.getKiller() instanceof Player player) {
            Entity victim = event.getEntity();
            String playerName = player.getName();

            if (victim instanceof Zombie) {
                if (!playerKillCache.containsKey(playerName)) {
                    playerKillCache.put(player.getUniqueId().toString(), 0);
                }
                playerKillCache.put(player.getUniqueId().toString(), playerKillCache.get(player.getUniqueId().toString()) + 1);

            }
        }

    }
}
