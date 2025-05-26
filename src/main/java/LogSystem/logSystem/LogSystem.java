package LogSystem.logSystem;

import LogSystem.logSystem.database.repository.MergeLogRepository;
import LogSystem.logSystem.database.repository.MonsterKillLogRepository;
import LogSystem.logSystem.database.repository.UpgradeLogRepository;
import LogSystem.logSystem.database.repository.UserLogRepository;
import LogSystem.logSystem.dto.MergeLogDto;
import LogSystem.logSystem.dto.MonsterKillLogDto;
import LogSystem.logSystem.dto.UpgradeDto;
import LogSystem.logSystem.dto.UserLogDto;
import LogSystem.logSystem.listener.MergeListener;
import LogSystem.logSystem.listener.MonsterListener;
import LogSystem.logSystem.listener.PlayerListener;
import LogSystem.logSystem.listener.UpgradeListener;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class LogSystem extends JavaPlugin {

    @Getter
    private static Map<String, MergeLogDto> mergeLogMap = new HashMap<>();
    @Getter
    private static Map<String, MonsterKillLogDto> monsterKillLogMap = new HashMap<>();
    @Getter
    private static Map<String, UpgradeDto> upgradeLogMap = new HashMap<>();
    @Getter
    private static Map<String, UserLogDto> userLogMap = new HashMap<>();
    @Getter
    private static LogSystem instance;

    @Override
    public void onEnable() {
        instance = this;
        register();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new MergeListener(), this);
//        this.getServer().getPluginManager().registerEvents(new MonsterListener(), this);
        this.getServer().getPluginManager().registerEvents(new UpgradeListener(), this);
    }

    @Override
    public void onDisable() {
    }

    private void register() {
        mergeLogMap = new MergeLogRepository().getMergeLog();
//        monsterKillLogMap = new MonsterKillLogRepository().loadMonsterKillLog();
        upgradeLogMap = new UpgradeLogRepository().getUpgradeLog();
        userLogMap = new UserLogRepository().getUserLog();
    }
}
