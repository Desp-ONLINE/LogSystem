package LogSystem.logSystem.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.desp.merge.event.MergeFailEvent;
import org.desp.merge.event.MergeSuccessEvent;
import org.desp.upgrade.event.UpgradeFailEvent;
import org.desp.upgrade.event.UpgradeFailandDistroyEvent;
import org.desp.upgrade.event.UpgradeSuccessEvent;
import org.jetbrains.annotations.NotNull;

public class LogSystemUtils {

    public static String getCurrentTime() {
        Date now = new Date();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = "-";
        dateTime = dateFormatter.format(now.getTime());
        return dateTime;
    }

    public static @NotNull Map<String, Integer> loadUsedMaterials(MergeSuccessEvent event) {
        List<Map<String, Integer>> material = event.getMergeItemInfo().getMaterials();
        return extractMaterials(material);
    }

    public static @NotNull Map<String, Integer> loadUsedMaterials(MergeFailEvent event) {
        List<Map<String, Integer>> material = event.getMergeItemInfo().getMaterials();
        return extractMaterials(material);
    }

    public static @NotNull Map<String, Integer> loadUsedMaterials(UpgradeSuccessEvent event) {
        List<Map<String, Integer>> material = event.getUpgradeData().getMaterials();
        return extractMaterials(material);
    }

    public static @NotNull Map<String, Integer> loadUsedMaterials(UpgradeFailEvent event) {
        List<Map<String, Integer>> material = event.getUpgradeData().getMaterials();
        return extractMaterials(material);
    }

    public static @NotNull Map<String, Integer> loadUsedMaterials(UpgradeFailandDistroyEvent event) {
        List<Map<String, Integer>> material = event.getUpgradeData().getMaterials();
        return extractMaterials(material);
    }

    private static @NotNull Map<String, Integer> extractMaterials(List<Map<String, Integer>> material) {
        Map<String, Integer> usedMaterials  = new HashMap<>();
        for (Map<String, Integer> stringIntegerMap : material) {
            usedMaterials.putAll(stringIntegerMap);
        }
        return usedMaterials;
    }
}
