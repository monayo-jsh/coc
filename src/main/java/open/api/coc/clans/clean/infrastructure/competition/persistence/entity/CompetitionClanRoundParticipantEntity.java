package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;


import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
    name = "tb_comp_clan_round_participant"
)
@Comment("대회 참여 클랜 라운드별 실제 참가자 테이블")
public class CompetitionClanRoundParticipantEntity implements Persistable<CompetitionClanRoundParticipantPK> {

    @EmbeddedId
    private CompetitionClanRoundParticipantPK id;

    @Comment("후보 여부")
    @Column(name = "is_backup", nullable = false)
    private Boolean isBackup;

    @MapsId("compClanScheduleId")
    @Comment("대회 참여 클랜 일정")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)) // 외래키 제약조건 설정하지 않음
    private CompetitionClanScheduleEntity competitionClanSchedule;

    @Transient
    private boolean isNew;

    @Builder
    private CompetitionClanRoundParticipantEntity(CompetitionClanRoundParticipantPK id, Boolean isBackup, boolean isNew) {
        this.id = id;
        this.isBackup = isBackup;
        this.isNew = isNew;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class CompetitionClanRoundParticipantsEntityBuilder {
        private Boolean isBackup = false;
        private boolean isNew = true;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public CompetitionClanRoundParticipantPK getId() {
        return this.id;
    }

    public String getPlayerTag() {
        return id.getPlayerTag();
    }
    
}
