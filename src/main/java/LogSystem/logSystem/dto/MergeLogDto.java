package LogSystem.logSystem.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MergeLogDto {
    private String user_id;
    private String uuid;
    private String target_item_id;
    private boolean success;
    private Map<String, Integer> used_materials;
}
