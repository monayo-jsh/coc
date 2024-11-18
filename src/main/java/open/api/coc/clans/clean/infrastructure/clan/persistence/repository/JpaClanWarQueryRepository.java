package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanWarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarDTO> findAllDTOByStartTime(LocalDateTime from, LocalDateTime to) {
        ConstructorExpression<ClanWarDTO> clanWarDTO = projectionClanWarDTO();
        return queryFactory.select(clanWarDTO)
                           .from(clanWarEntity)
                           .join(clanEntity).on(clanEntity.tag.eq(clanWarEntity.clanTag))
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

    public Optional<ClanWarDTO> findDTOById(Long warId) {
        ConstructorExpression<ClanWarDTO> clanWarDTO = projectionClanWarDTO();

        ClanWarDTO clanWar = queryFactory.select(clanWarDTO)
                                         .from(clanWarEntity)
                                         .join(clanEntity).on(clanEntity.tag.eq(clanWarEntity.clanTag))
                                         .where(clanWarEntity.warId.eq(warId))
                                         .fetchOne();

        return Optional.ofNullable(clanWar);
    }

    private ConstructorExpression<ClanWarDTO> projectionClanWarDTO() {
        return Projections.constructor(ClanWarDTO.class,
                                       clanWarEntity.warId,
                                       clanEntity.name.as("clanName"),
                                       clanWarEntity.state,
                                       clanWarEntity.type,
                                       clanWarEntity.battleType,
                                       clanWarEntity.preparationStartTime,
                                       clanWarEntity.startTime,
                                       clanWarEntity.endTime,
                                       clanWarEntity.teamSize,
                                       clanWarEntity.attacksPerMember,
                                       clanWarEntity.warTag);
    }

}
