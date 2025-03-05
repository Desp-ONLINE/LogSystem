package LogSystem.logSystem.listener;

import static LogSystem.logSystem.utils.LogSystemUtils.loadUsedMaterials;

import LogSystem.logSystem.database.repository.UpgradeLogRepository;
import LogSystem.logSystem.dto.UpgradeDto;
import LogSystem.logSystem.utils.LogSystemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.desp.upgrade.event.UpgradeFailEvent;
import org.desp.upgrade.event.UpgradeFailandDistroyEvent;
import org.desp.upgrade.event.UpgradeSuccessEvent;

public class UpgradeListener implements Listener {
    @EventHandler
    public void onUpgradeSuccessEvent(UpgradeSuccessEvent event) {
        Player player = event.getPlayer();

        UpgradeDto successData = UpgradeDto.builder()
                .user_id(player.getName())
                .uuid(player.getUniqueId().toString())
                .item_id(event.getUpgradeData().getBeforeWeapon())
                .success(true)
                .destroy(false)
                .used_materials(loadUsedMaterials(event))
                .build();

        UpgradeLogRepository.insertUpgradeLog(successData, LogSystemUtils.getCurrentTime());
    }

    @EventHandler
    public void onUpgradeFailEvent(UpgradeFailEvent event) {
        Player player = event.getPlayer();
        UpgradeDto failData = UpgradeDto.builder()
                .user_id(player.getName())
                .uuid(player.getUniqueId().toString())
                .item_id(event.getUpgradeData().getBeforeWeapon())
                .success(false)
                .destroy(false)
                .used_materials(loadUsedMaterials(event))
                .build();

        UpgradeLogRepository.insertUpgradeLog(failData, LogSystemUtils.getCurrentTime());
    }

    @EventHandler
    public void onUpgradeFailAndDestroyEvent(UpgradeFailandDistroyEvent event) {
        Player player = event.getPlayer();
        UpgradeDto failAndDestroyData = UpgradeDto.builder()
                .user_id(player.getName())
                .uuid(player.getUniqueId().toString())
                .item_id(event.getUpgradeData().getBeforeWeapon())
                .success(false)
                .destroy(true)
                .used_materials(loadUsedMaterials(event))
                .build();

        UpgradeLogRepository.insertUpgradeLog(failAndDestroyData, LogSystemUtils.getCurrentTime());
    }
}
