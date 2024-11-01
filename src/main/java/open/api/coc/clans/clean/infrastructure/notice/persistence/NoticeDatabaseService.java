package open.api.coc.clans.clean.infrastructure.notice.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.notice.model.Notice;
import open.api.coc.clans.clean.domain.notice.repository.NoticeRepository;
import open.api.coc.clans.clean.infrastructure.notice.persistence.repository.JpaNoticeCustomRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeDatabaseService implements NoticeRepository {

    private final JpaNoticeCustomRepository noticeCustomRepository;

    @Override
    public List<Notice> findAllPosting() {
        return noticeCustomRepository.findAllPosting();
    }
}
