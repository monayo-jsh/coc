package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;
import open.api.coc.clans.clean.infrastructure.clan.persistence.repository.query.ClanWarMemberMissingAttackConditionBuilder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CLanWarMemberAttackQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarMemberMissingAttackDTO> findMissingAttackPlayers(LocalDateTime from, LocalDateTime to) {
        ClanWarMemberMissingAttackConditionBuilder builder = new ClanWarMemberMissingAttackConditionBuilder(from, to);
        BooleanBuilder condition = builder.build();
        return buildMissingAttackQuery(condition).fetch();
    }

    private JPAQuery<ClanWarMemberMissingAttackDTO> buildMissingAttackQuery(BooleanBuilder condition) {
        ConstructorExpression<ClanWarMemberMissingAttackDTO> missingAttackDTO = Projections.constructor(
            ClanWarMemberMissingAttackDTO.class,
            clanEntity.name,
            clanEntity.order,
            clanWarEntity.warId,
            clanWarEntity.type,
            clanWarEntity.state,
            clanWarEntity.startTime,
            clanWarEntity.endTime,
            clanWarMemberEntity.id.tag,
            clanWarMemberEntity.name,
            clanWarMemberEntity.necessaryAttackYn
        );

        return queryFactory.select(missingAttackDTO)
                           .from(clanWarEntity)
                           .join(clanEntity).on(clanEntity.tag.eq(clanWarEntity.clanTag))
                           .join(clanWarEntity.members, clanWarMemberEntity)
                           .leftJoin(clanWarMemberEntity.attacks, clanWarMemberAttackEntity)
                           .where(condition)
                           .groupBy(clanWarMemberEntity.id.warId, clanWarMemberEntity.id.tag)
                           .having(clanWarMemberAttackEntity.id.order.count().lt(clanWarEntity.attacksPerMember))
                           .orderBy(clanWarEntity.warId.asc(), clanWarMemberEntity.mapPosition.asc());
    }
}
