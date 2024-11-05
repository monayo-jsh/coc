package open.api.coc.clans.clean.infrastructure.notice.persistence.repository;

import static open.api.coc.clans.clean.domain.notice.model.QNotice.notice;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.notice.model.Notice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaNoticeCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<Notice> findAll() {
        return queryFactory.select(notice)
                           .from(notice)
                           .leftJoin(notice.detail).fetchJoin()
                           .fetch();
    }

    public List<Notice> findAllPosting() {
        LocalDateTime now = LocalDateTime.now();

        BooleanBuilder condition = new BooleanBuilder();
        condition.and(notice.postingStartDate.before(now))
                 .and(notice.postingEndDate.after(now));

        return queryFactory.select(notice)
                           .from(notice)
                           .where(condition)
                           .fetch();
    }

}
