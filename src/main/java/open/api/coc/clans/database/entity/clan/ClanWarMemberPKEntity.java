package open.api.coc.clans.database.entity.clan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ClanWarMemberPKEntity implements Serializable {

    @Column(name = "war_id", nullable = false)
    private Long warId;

    @Column(name = "tag", nullable = false)
    private String tag;

}
