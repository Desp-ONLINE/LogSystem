package LogSystem.logSystem.listener;

import static LogSystem.logSystem.utils.LogSystemUtils.getCurrentTime;

import LogSystem.logSystem.database.repository.UserLogRepository;
import LogSystem.logSystem.dto.UserLogDto;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!UserLogRepository.findUser(player.getUniqueId().toString())) {
            UserLogDto newUser = UserLogDto.builder()
                    .user_id(player.getName())
                    .uuid(player.getUniqueId().toString())
                    .firstJoin(getCurrentTime())
                    .lastConnection(null)
                    .build();

            UserLogRepository.insertUserLog(newUser);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserLogRepository.updateUserLog(player.getUniqueId().toString(), getCurrentTime());
    }
}
