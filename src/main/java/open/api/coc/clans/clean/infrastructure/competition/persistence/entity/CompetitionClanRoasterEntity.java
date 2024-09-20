package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
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
    name = "tb_comp_clan_roaster"
)
@Comment("대회 참여 클랜 전체 등록 멤버 테이블")
public class CompetitionClanRoasterEntity implements Persistable<CompetitionClanRoasterPK> {

    @EmbeddedId
    private CompetitionClanRoasterPK id;

    // 단순 조회용
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comp_clan_id", insertable = false, updatable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)) // 외래키 제약조건 설정하지 않음
    private CompetitionClanEntity competitionClan;

    @Transient
    private boolean isNew;

    @Builder
    private CompetitionClanRoasterEntity(CompetitionClanRoasterPK id, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class CompetitionClanRoasterEntityBuilder {
        private boolean isNew = true;
    }


    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public CompetitionClanRoasterPK getId() {
        return id;
    }

    public String getPlayerTag() {
        return id.getPlayerTag();
    }

}
