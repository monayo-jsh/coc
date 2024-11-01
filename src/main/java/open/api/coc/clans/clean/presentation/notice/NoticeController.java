package open.api.coc.clans.clean.presentation.notice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.notice.NoticeService;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공지사항", description = "공지사항 관리 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(
        summary = "게시중인 공지사항 목록을 조회합니다. version: 1.00, Last Update: 24.11.01",
        description = "이 API는 게시중인 공지사항 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoticeResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/posting")
    public ResponseEntity<List<NoticeResponse>> getPostingNotices() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(noticeService.getPostingNotices());
    }

}
