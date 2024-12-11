package open.api.coc.clans.clean.infrastructure.common.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Embeddable
public class IconUrlEntity {

    @Column(name = "tiny", nullable = true, length = 500)
    @Comment("가장 작은 아이콘 경로")
    private String tiny;

    @Column(name = "small", nullable = true, length = 500)
    @Comment("작은 아이콘 경로")
    private String small;

    @Column(name = "medium", nullable = true, length = 500)
    @Comment("중간 아이콘 경로")
    private String medium;

    @Column(name = "large", nullable = true, length = 500)
    @Comment("큰 아이콘 경로")
    private String large;

    @Builder
    private IconUrlEntity(String tiny, String small, String medium, String large) {
        this.tiny = tiny;
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

}
