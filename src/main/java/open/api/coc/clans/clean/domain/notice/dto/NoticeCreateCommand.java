package open.api.coc.clans.clean.domain.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.notice.model.NoticeType;

public record NoticeCreateCommand(

    @Schema(description = "공지 유형")
    NoticeType type,

    @Schema(description = "한줄 공지 내용")
    String oneLine,

    @Schema(description = "상세 내용")
    String content,

    @Schema(description = "게시 시작일시")
    LocalDateTime postingStartDate,

    @Schema(description = "게시 종료일시")
    LocalDateTime postingEndDate,

    @Schema(description = "종료 타이머 설정 여부")
    Boolean timerEnabled,

    @Schema(description = "노출 설정 여부")
    Boolean isVisible

) {
}
