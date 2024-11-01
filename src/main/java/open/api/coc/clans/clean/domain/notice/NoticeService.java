package open.api.coc.clans.clean.domain.notice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.notice.mapper.NoticeMapper;
import open.api.coc.clans.clean.domain.notice.model.Notice;
import open.api.coc.clans.clean.domain.notice.repository.NoticeRepository;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final NoticeMapper noticeMapper;

    public List<NoticeResponse> getPostingNotices() {

        List<Notice> notices = noticeRepository.findAllPosting();

        return notices.stream()
                      .map(noticeMapper::toNoticeResponse)
                      .toList();
    }
}