package open.api.coc.clans.database.entity.raid;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_RAIDER")
@NoArgsConstructor
@Getter
public class RaiderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RAIDER_SEQ")
    private Long id;
    private String tag;
    private String name;
    private Integer attacks;
    private Integer resourceLooted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "raid_id")
    private RaidEntity raid;

    @Builder
    public RaiderEntity(String tag, String name, Integer attacks, Integer resourceLooted, RaidEntity raid) {
        this.tag = tag;
        this.name = name;
        this.attacks = attacks;
        this.resourceLooted = resourceLooted;
        this.raid = raid;
    }
}
