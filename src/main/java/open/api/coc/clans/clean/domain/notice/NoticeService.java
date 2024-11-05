package open.api.coc.clans.clean.domain.notice;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.notice.dto.NoticeCreateCommand;
import open.api.coc.clans.clean.domain.notice.exception.NoticeNotFoundException;
import open.api.coc.clans.clean.domain.notice.mapper.NoticeMapper;
import open.api.coc.clans.clean.domain.notice.model.Notice;
import open.api.coc.clans.clean.domain.notice.repository.NoticeRepository;
import open.api.coc.clans.clean.presentation.notice.dto.NoticeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final NoticeMapper noticeMapper;

    @Transactional(readOnly = true)
    public List<NoticeResponse> getNotices() {
        List<Notice> notices = noticeRepository.findAll();

        return notices.stream()
                      .map(noticeMapper::toNoticeResponse)
                      .sorted(Comparator.comparing(NoticeResponse::id).reversed())
                      .toList();
    }

    @Transactional(readOnly = true)
    public List<NoticeResponse> getPostingNotices() {
        List<Notice> notices = noticeRepository.findAllPosting();

        return notices.stream()
                      .map(noticeMapper::toNoticeResponse)
                      .toList();
    }

    @Transactional
    public void registerNotice(NoticeCreateCommand command) {
        Notice notice = Notice.createNew(command.type(),
                                         command.oneLine(),
                                         command.content(),
                                         command.postingStartDate(),
                                         command.postingEndDate(),
                                         command.timerEnabled(),
                                         command.isVisible());

        noticeRepository.save(notice);
    }

    public void changeVisible(Long noticeId) {
        // 공지사항 조회
        Notice notice = noticeRepository.findById(noticeId)
                                        .orElseThrow(() -> new NoticeNotFoundException(noticeId));

        // 노출 설정 여부 수정
        notice.changeVisible();

        // 업데이트
        noticeRepository.save(notice);
    }

}
