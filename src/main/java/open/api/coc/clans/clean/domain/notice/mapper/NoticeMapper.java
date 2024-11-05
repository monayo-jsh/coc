package open.api.coc.clans.clean.domain.notice.mapper;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.notice.dto.NoticeCreateCommand;
import open.api.coc.clans.clean.domain.notice.model.Notice;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeCreateReqeust;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    config = MapStructConfig.class
)
public abstract class NoticeMapper {

    @Autowired
    private TimeUtils timeUtils;

    @Mapping(target = "content", source = "detail.content")
    public abstract NoticeResponse toNoticeResponse(Notice notice);

    @Mapping(target = "postingStartDate", source = "startDateTime")
    @Mapping(target = "postingEndDate", source = "endDateTime")
    @Mapping(target = "timerEnabled", source = "shutdownTimer")
    public abstract NoticeCreateCommand toCreateCommand(@Valid NoticeCreateReqeust request);

    protected LocalDateTime map(Long dateTime) {
        return timeUtils.toLocalDateTime(dateTime);
    }

}
