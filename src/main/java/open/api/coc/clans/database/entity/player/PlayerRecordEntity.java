package open.api.coc.clans.database.entity.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_player_record"
)
@Comment("플레이어 기록 설정 테이블")
public class PlayerRecordEntity implements Persistable<String> {

    @Comment("플레이어 태그")
    @Id
    @Column(name = "tag", nullable = false, length = 100)
    private String tag;

    @Transient
    private boolean isNew;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.tag;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
