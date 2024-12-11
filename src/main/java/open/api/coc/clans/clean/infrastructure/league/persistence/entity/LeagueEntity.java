package open.api.coc.clans.clean.infrastructure.league.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.infrastructure.common.persistence.entity.IconUrlEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_league")
@Comment("리그 테이블")
public class LeagueEntity implements Persistable<Integer> {

    @Comment("리그 고유키")
    @Id
    @Column(name = "league_id", nullable = false, length = 255)
    private Integer id;

    @Comment("리그 이름")
    @Column(name = "league_name", nullable = false, length = 255)
    private String name;

    @Comment("리그 아이콘")
    @Embedded
    private IconUrlEntity iconUrl;

    @Transient
    private boolean isNew;

    @Builder
    private LeagueEntity(Integer id, String name, IconUrlEntity iconUrl, boolean isNew) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.isNew = isNew;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class LeagueEntityBuilder {

        private boolean isNew = true;
        private List<PlayerEntity> players = new ArrayList<>();

    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    public void markedNotNew() { this.markNotNew(); }

    @Override
    public Integer getId() {
        return this.id;
    }

}
