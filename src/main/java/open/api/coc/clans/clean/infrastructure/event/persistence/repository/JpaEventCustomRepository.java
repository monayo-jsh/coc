package open.api.coc.clans.clean.infrastructure.event.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.event.persistence.entity.QEventEntity.eventEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventEntity;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaEventCustomRepository {

    private final JPAQueryFactory queryFactory;

    private BooleanBuilder createBaseCondition() {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(eventEntity.type.eq(EventType.TEAM_LEGEND));
        return condition;
    }

    public LocalDateTime findLatestStartDate() {
        BooleanBuilder condition = createBaseCondition();

        return queryFactory.select(eventEntity.startDate.max())
                           .from(eventEntity)
                           .where(condition)
                           .groupBy(eventEntity.startDate)
                           .fetchOne();
    }

    public Optional<EventEntity> findByStartDate(LocalDateTime startDate) {
        BooleanBuilder condition = createBaseCondition();
        condition.and(eventEntity.startDate.eq(startDate));

        EventEntity findEvent = queryFactory.select(eventEntity)
                                            .from(eventEntity)
                                            .where(condition)
                                            .fetchOne();

        return Optional.ofNullable(findEvent);
    }
}
