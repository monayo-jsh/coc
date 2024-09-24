package open.api.coc.clans.clean.infrastructure.season.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_season_end_management"
)
@Comment("시즌 종료 관리 테이블")
public class SeasonEndManagementEntity {

    @Id
    @Column(name = "end_date")
    @Comment("시즌 종료일")
    private LocalDate endDate;

}
