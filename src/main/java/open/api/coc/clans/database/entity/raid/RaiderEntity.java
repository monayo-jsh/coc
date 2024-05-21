package open.api.coc.clans.database.entity.raid;

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
    private Integer resourceLooted;

    @Builder
    public RaiderEntity(String tag, String name, Integer resourceLooted) {
        this.tag = tag;
        this.name = name;
        this.resourceLooted = resourceLooted;
    }
}
