package open.api.coc.clans.clean.presentation.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record NoticeResponse(

    @Schema(description = "구분 (NOTICE: 공지, EVENT: 이벤트)")
    String type,

    @Schema(description = "한줄 공지 문구")
    String oneLine

) {
}
