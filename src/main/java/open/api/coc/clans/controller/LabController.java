package open.api.coc.clans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.lab.LabEntity;
import open.api.coc.clans.database.repository.lab.LabRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "연구소", description = "연구소 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/lab")
public class LabController {

    private final LabRepository labRepository;

    @Operation(
        summary = "연구소 목록 조회 API, version: 1.00, Last Update: 24.08.19",
        description = "연구소 목록 조회 API"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LabEntity.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/link/all")
    public ResponseEntity<List<LabEntity>> getLinks() {
        return ResponseEntity.ok()
                             .body(labRepository.findAll());
    }

}
