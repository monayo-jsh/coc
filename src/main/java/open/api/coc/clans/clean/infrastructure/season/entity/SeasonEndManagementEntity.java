package open.api.coc.clans.clean.infrastructure.season.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_season_end_management"
)
@Comment("시즌 종료 관리 테이블")
public class SeasonEndManagementEntity implements Persistable<LocalDate> {

    @Id
    @Column(name = "end_date")
    @Comment("시즌 종료일")
    private LocalDate endDate;

    @Transient
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    public void markNotNew() {
        this.isNew = false;
    }

    @Override
    public LocalDate getId() {
        return this.endDate;
    }

    public static SeasonEndManagementEntity createNew(LocalDate endDate) {
        SeasonEndManagementEntity entity = new SeasonEndManagementEntity();
        entity.endDate = endDate;
        return entity;
    }

}
