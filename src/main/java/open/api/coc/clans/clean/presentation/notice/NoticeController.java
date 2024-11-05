package open.api.coc.clans.clean.presentation.notice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.notice.NoticeService;
import open.api.coc.clans.clean.domain.notice.dto.NoticeCreateCommand;
import open.api.coc.clans.clean.domain.notice.mapper.NoticeMapper;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeCreateReqeust;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공지사항", description = "공지사항 관리 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    private final NoticeMapper noticeMapper;

    @Operation(
        summary = "전체 공지사항 목록을 조회합니다. version: 1.00, Last Update: 24.11.04",
        description = "이 API는 전체 공지사항 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NoticeResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<NoticeResponse>> getNotices() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(noticeService.getNotices());
    }

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

    @Operation(
        summary = "공지사항을 등록합니다. version: 1.00, Last Update: 24.11.05",
        description = "이 API는 공지사항을 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("")
    public ResponseEntity<Void> postNotice(@Valid @RequestBody NoticeCreateReqeust request) {
        NoticeCreateCommand command = noticeMapper.toCreateCommand(request);
        noticeService.registerNotice(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    @Operation(
        summary = "공지사항의 종료타이머 여부를 변경합니다. version: 1.00, Last Update: 24.11.05",
        description = "이 API는 공지사항의 종료타이머 여부를 변경합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("{noticeId}/shutdown-timer")
    public ResponseEntity<Void> putNoticeShutdownTimer(@PathVariable Long noticeId) {
        noticeService.changeShutdownTimer(noticeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    @Operation(
        summary = "공지사항의 노출 여부를 변경합니다. version: 1.00, Last Update: 24.11.05",
        description = "이 API는 공지사항의 노출 여부를 변경합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("{noticeId}/visible")
    public ResponseEntity<Void> putNoticeVisible(@PathVariable Long noticeId) {
        noticeService.changeVisible(noticeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

}
