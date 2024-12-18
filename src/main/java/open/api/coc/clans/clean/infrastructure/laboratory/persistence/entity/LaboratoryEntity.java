package open.api.coc.clans.clean.infrastructure.laboratory.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "tb_lab")
public class LaboratoryEntity {

    @Schema(description = "연구소 고유아이디")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "연구소 방 이름")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "연구소 방 링크")
    @Column(name = "link", nullable = false)
    private String link;

    @Schema(description = "정렬 순서")
    @Column(name = "orders", nullable = true)
    private Integer order;

    public static LaboratoryEntity createNew(String name, String link) {
        return LaboratoryEntity.builder()
                               .name(name)
                               .link(link)
                               .order(Integer.MAX_VALUE)
                               .build();
    }

}
