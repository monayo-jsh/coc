package open.api.coc.clans.clean.presentation.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NoticeCreateReqeust(

    @Schema(description = "공지 유형")
    @Pattern(regexp = "NOTICE|EVENT|COUPON")
    String type,

    @Schema(description = "한줄 공지 내용")
    @NotEmpty(message = "한줄 공지 내용을 입력해주세요.")
    @Size(max = 20)
    String oneLine,

    @Schema(description = "상세 내용")
    @Size(max = 2000)
    String content,

    @Schema(description = "게시 시작일시")
    @NotNull
    Long startDateTime,

    @Schema(description = "게시 종료일시")
    @NotNull
    Long endDateTime,

    @Schema(description = "종료 타이머 설정 여부")
    @NotNull
    Boolean shutdownTimer,

    @Schema(description = "노출 설정 여부")
    @NotNull
    Boolean isVisible

) {
}
