package open.api.coc.clans.clean.presentation.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record NoticeResponse(

    @Schema(description = "공지 고유키")
    Long id,

    @Schema(description = "구분 (NOTICE: 공지, EVENT: 이벤트)")
    String type,

    @Schema(description = "한줄 공지 문구")
    String oneLine,

    @Schema(description = "게시 시작일시")
    String postingStartDate,

    @Schema(description = "게시 종료일시")
    String postingEndDate,

    @Schema(description = "종료시간까지 타이머 활성화 여부")
    Boolean timerEnabled

) {
}
