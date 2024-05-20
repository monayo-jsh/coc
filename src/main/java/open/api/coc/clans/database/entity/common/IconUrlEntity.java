package open.api.coc.clans.database.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class IconUrlEntity {

    @Column(name = "tiny", length = 500)
    private String tiny;

    @Column(name = "small", length = 500)
    private String small;

    @Column(name = "medium", length = 500)
    private String medium;

    @Column(name = "large", length = 500)
    private String large;

}
