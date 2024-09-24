package open.api.coc.clans.clean.infrastructure.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Builder
@Getter @Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
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

}
