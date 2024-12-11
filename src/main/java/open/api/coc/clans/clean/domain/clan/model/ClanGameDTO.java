package open.api.coc.clans.clean.domain.clan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@AllArgsConstructor
public class ClanGameDTO {

    @Comment("진행월: yyyy-MM")
    private String progressDate;

    @Schema(description = "플레이어 태그")
    private String playerTag;

    @Schema(description = "플레이어 이름")
    private String playerName;

    @Comment("획득 점수")
    private Integer point;

    @Comment("마지막 진행일시")
    private LocalDateTime lastModifiedAt;

}
