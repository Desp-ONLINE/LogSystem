package LogSystem.logSystem.listener;

import static LogSystem.logSystem.utils.LogSystemUtils.getCurrentTime;
import static LogSystem.logSystem.utils.LogSystemUtils.loadUsedMaterials;

import LogSystem.logSystem.database.repository.MergeLogRepository;
import LogSystem.logSystem.dto.MergeLogDto;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.desp.merge.event.MergeFailEvent;
import org.desp.merge.event.MergeSuccessEvent;

public class MergeListener implements Listener {
    @EventHandler
    public void onMergeSuccessEvent(MergeSuccessEvent event) {
        Player player = event.getPlayer();

        MergeLogDto successData = MergeLogDto.builder()
                .user_id(player.getName())
                .uuid(player.getUniqueId().toString())
                .target_item_id(event.getMergeItemInfo().getAfterWeapon())
                .success(true)
                .used_materials(loadUsedMaterials(event))
                .build();

        MergeLogRepository.insertMergeLog(successData, getCurrentTime());
    }

    @EventHandler
    public void onMergeFailEvent(MergeFailEvent event) {
        Player player = event.getPlayer();
        MergeLogDto failData = MergeLogDto.builder()
                .user_id(player.getName())
                .uuid(player.getUniqueId().toString())
                .target_item_id(event.getMergeItemInfo().getAfterWeapon())
                .success(false)
                .used_materials(loadUsedMaterials(event))
                .build();

        MergeLogRepository.insertMergeLog(failData, getCurrentTime());
    }
}
