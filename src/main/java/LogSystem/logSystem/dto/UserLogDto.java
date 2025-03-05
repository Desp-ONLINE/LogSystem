package LogSystem.logSystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserLogDto {
    private String user_id;
    private String uuid;
    private String firstJoin;
    private String lastConnection;


}
