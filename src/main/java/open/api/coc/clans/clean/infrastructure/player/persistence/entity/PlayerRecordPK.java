package open.api.coc.clans.clean.infrastructure.player.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PlayerRecordPK implements Serializable {

    @Comment("플레이어 태그")
    @Column(name = "tag", nullable = false, length = 100)
    private String tag;

    @Comment("시즌")
    @Column(name = "season", nullable = false)
    private String season;

    public static PlayerRecordPK create(String tag, String season) {
        return PlayerRecordPK.builder()
                             .tag(tag)
                             .season(season)
                             .build();
    }
}
