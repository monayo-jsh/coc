package open.api.coc.clans.clean.domain.notice.mapper;

import open.api.coc.clans.clean.domain.notice.model.Notice;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(
    config = MapStructConfig.class
)
public interface NoticeMapper {

    NoticeResponse toNoticeResponse(Notice notice);

}
