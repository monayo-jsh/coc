package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanWarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarEntity> findAllByStartTime(LocalDateTime from, LocalDateTime to) {
        return queryFactory.selectFrom(clanWarEntity)
                           .where(clanWarEntity.startTime.between(from, to))
                           .orderBy(clanWarEntity.warId.asc())
                           .fetch();
    }


    public Optional<ClanWarEntity> findById(Long warId) {
        ClanWarEntity findClanWar = queryFactory.select(clanWarEntity)
                                                .from(clanWarEntity)
                                                .where(clanWarEntity.warId.eq(warId))
                                                .fetchOne();

        return Optional.ofNullable(findClanWar);
    }
}
