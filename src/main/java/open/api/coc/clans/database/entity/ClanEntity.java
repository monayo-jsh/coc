package open.api.coc.clans.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_clan")
public class ClanEntity {

    @Id
    @Column(name = "tag", length = 100)
    private String tag;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "orders", nullable = false)
    private Integer order;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

}
