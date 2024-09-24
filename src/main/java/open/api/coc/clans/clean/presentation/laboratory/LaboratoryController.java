package open.api.coc.clans.clean.presentation.laboratory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.laboratory.entity.LaboratoryEntity;
import open.api.coc.clans.clean.infrastructure.laboratory.repository.JpaLaboratoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "연구소", description = "연구소 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lab")
public class LaboratoryController {

    private final JpaLaboratoryRepository jpaLaboratoryRepository;

    @Operation(
        summary = "연구소 방 목록을 조회합니다., version: 1.00, Last Update: 24.08.19",
        description = "이 API는 연구소 방 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LaboratoryEntity.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/links")
    public ResponseEntity<List<LaboratoryEntity>> getLinks() {
        return ResponseEntity.ok()
                             .body(jpaLaboratoryRepository.findAll());
    }

}
