package open.api.coc.clans.clean.infrastructure.laboratory.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_lab_meta")
public class LaboratoryMetaEntity {

    @Schema(description = "연구소 입장코드 고유아이디")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "연구소 입장코드 이름")
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Schema(description = "연구소 입장코드 등록일")
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public static LaboratoryMetaEntity createNew(String code) {
        return LaboratoryMetaEntity.builder()
                                   .code(code)
                                   .build();
    }

}
