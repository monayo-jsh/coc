package open.api.coc.clans.clean.infrastructure.notice.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.notice.model.Notice;
import open.api.coc.clans.clean.domain.notice.repository.NoticeRepository;
import open.api.coc.clans.clean.infrastructure.notice.persistence.repository.JpaNoticeCustomRepository;
import open.api.coc.clans.clean.infrastructure.notice.persistence.repository.JpaNoticeRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeDatabaseService implements NoticeRepository {

    private final JpaNoticeRepository noticeRepository;
    private final JpaNoticeCustomRepository noticeCustomRepository;

    @Override
    public List<Notice> findAll() {
        return noticeCustomRepository.findAll();
    }

    @Override
    public List<Notice> findAllPosting() {
        return noticeCustomRepository.findAllPosting();
    }

    @Override
    public Optional<Notice> findById(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }

    @Override
    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }
}
