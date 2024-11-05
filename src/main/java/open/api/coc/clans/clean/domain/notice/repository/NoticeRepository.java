package open.api.coc.clans.clean.domain.notice.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.notice.model.Notice;

public interface NoticeRepository {

    List<Notice> findAll();
    List<Notice> findAllPosting();

    Optional<Notice> findById(Long noticeId);
    Notice save(Notice notice);


}
