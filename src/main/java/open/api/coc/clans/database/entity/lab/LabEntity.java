package open.api.coc.clans.database.entity.lab;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_lab")
public class LabEntity {

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

}
