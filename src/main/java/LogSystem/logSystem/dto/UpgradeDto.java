package LogSystem.logSystem.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UpgradeDto {
    private String user_id;
    private String uuid;
    private String item_id;
    private boolean success;
    private boolean destroy;
    private Map<String, Integer> used_materials;


}
