package open.api.coc.clans.database.entity.raid;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Table(name = "TB_RAID")
@Entity
@Getter
@NoArgsConstructor
public class RaidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RAID_ID")
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String clanTag;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "RAID_ID")
    private List<RaiderEntity> raiderEntityList;

    @Builder
    public RaidEntity(LocalDate startDate, LocalDate endDate, String clanTag, List<RaiderEntity> radierEntityList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.clanTag = clanTag;
        this.raiderEntityList = radierEntityList;
    }
}
