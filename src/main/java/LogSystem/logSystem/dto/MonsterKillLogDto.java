package LogSystem.logSystem.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MonsterKillLogDto {
    private String user_id;
    private String uuid;
    private Map<String, Integer> kills;
}
