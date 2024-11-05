package open.api.coc.clans.clean.domain.notice.mapper;

import open.api.coc.clans.clean.domain.notice.model.Notice;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface NoticeMapper {

    @Mapping(target = "content", source = "detail.content")
    NoticeResponse toNoticeResponse(Notice notice);

}
