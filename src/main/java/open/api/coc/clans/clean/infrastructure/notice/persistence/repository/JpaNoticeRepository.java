package open.api.coc.clans.clean.infrastructure.notice.persistence.repository;

import open.api.coc.clans.clean.domain.notice.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNoticeRepository extends JpaRepository<Notice, Long> {

}
