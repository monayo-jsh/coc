package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_comp_player",
    indexes = {
        @Index(name = "idx_comp_clan_clan_tag", columnList = "clan_tag")
    }
)
@Comment("대회팀 멤버 테이블")
public class CompetitionPlayerEntity implements Persistable<String> {

    @Comment("플레이어 태그")
    @Id
    @Column(name = "player_tag", nullable = false, length = 100)
    private String playerTag;

    @Comment("플레이어 이름")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Comment("플레이어 닉네임")
    @Column(name = "nickname", nullable = true, length = 10)
    private String nickname;

    @Comment("주조합")
    @Column(name = "one_troop", nullable = true, length = 50)
    private String oneTroop;

    @Comment("서브조합")
    @Column(name = "two_troop", nullable = true, length = 50)
    private String twoTroop;

    @Comment("서브조합")
    @Column(name = "three_troop", nullable = true, length = 50)
    private String threeTroop;

    @Comment("소속 클랜")
    @Column(name = "clan_tag", nullable = true, length = 100)
    private String clanTag;

    @Transient
    @JsonIgnore
    private boolean isNew = true;

    @Builder
    private CompetitionPlayerEntity(String playerTag, String name, String nickname, String oneTroop,
                                   String twoTroop, String threeTroop, String clanTag,
                                   boolean isNew) {
        this.playerTag = playerTag;
        this.name = name;
        this.nickname = nickname;
        this.oneTroop = oneTroop;
        this.twoTroop = twoTroop;
        this.threeTroop = threeTroop;
        this.clanTag = clanTag;
        this.isNew = isNew;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class CompetitionPlayerEntityBuilder {
        private boolean isNew = true;
    }

    @Override
    public String getId() {
        return this.playerTag;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}