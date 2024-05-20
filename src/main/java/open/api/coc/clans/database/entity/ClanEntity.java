package open.api.coc.clans.database.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_clan")
public class ClanEntity implements Persistable<String> {

    @Id
    @Column(name = "tag", length = 100)
    private String tag;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "orders", nullable = false)
    private Integer order;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag")
    private ClanContentEntity clanContent;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.tag;
    }

    public void changeClanContent(ClanContentEntity clanContent) {
        this.clanContent = clanContent;
    }
}
