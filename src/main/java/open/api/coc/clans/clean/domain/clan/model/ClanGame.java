package open.api.coc.clans.clean.domain.clan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
public class ClanGame {

    @Schema(description = "클랜게임 진행 고유키")
    private Long id;

    @Schema(description = "플레이어 태그")
    private String playerTag;

    @Comment("진행월: yyyy-MM")
    private String progressDate;

    @Comment("시작 점수")
    private Integer startPoint;

    @Comment("진행 점수")
    private Integer finishPoint;

    @Comment("마지막 반영일시")
    private LocalDateTime lastModifiedAt;

    @Builder
    private ClanGame(Long id, String playerTag, String progressDate, Integer startPoint, Integer finishPoint, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.playerTag = playerTag;
        this.progressDate = progressDate;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static ClanGame create(String playerTag, String newSeasonProgressDate, Integer origClanGamePoint) {
        return ClanGame.builder()
                       .playerTag(playerTag)
                       .progressDate(newSeasonProgressDate)
                       .startPoint(origClanGamePoint)
                       .finishPoint(origClanGamePoint)
                       .lastModifiedAt(LocalDateTime.now())
                       .build();
    }

    public void changeFinishPoint(Integer clanGamePoint) {
        if (Objects.equals(this.finishPoint, clanGamePoint)) return;

        this.finishPoint = clanGamePoint;
        this.lastModifiedAt = LocalDateTime.now();
    }
}
