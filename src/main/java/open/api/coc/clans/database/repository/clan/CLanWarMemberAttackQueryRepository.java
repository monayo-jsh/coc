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
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantMissingAttackDTO;
import open.api.coc.clans.clean.infrastructure.clan.persistence.repository.query.ClanWarMemberMissingAttackConditionBuilder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CLanWarMemberAttackQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarParticipantMissingAttackDTO> findMissingAttacksByStartTime(LocalDateTime from, LocalDateTime to) {
        ClanWarMemberMissingAttackConditionBuilder condition = new ClanWarMemberMissingAttackConditionBuilder(from, to);
        return buildMissingAttackQuery(condition.build()).fetch();
    }

    public List<ClanWarParticipantMissingAttackDTO> findMissingAttacksByTagAndStartTime(String tag, LocalDateTime from, LocalDateTime to) {
        ClanWarMemberMissingAttackConditionBuilder condition = new ClanWarMemberMissingAttackConditionBuilder(from, to);
        condition = condition.withPlayerTag(tag);
        return buildMissingAttackQuery(condition.build()).fetch();
    }

    public List<ClanWarParticipantMissingAttackDTO> findMissingAttacksByNameAndStartTime(String name, LocalDateTime from, LocalDateTime to) {
        ClanWarMemberMissingAttackConditionBuilder condition = new ClanWarMemberMissingAttackConditionBuilder(from, to);
        condition = condition.withPlayerName(name);
        return buildMissingAttackQuery(condition.build()).fetch();
    }

    private JPAQuery<ClanWarParticipantMissingAttackDTO> buildMissingAttackQuery(BooleanBuilder condition) {
        ConstructorExpression<ClanWarParticipantMissingAttackDTO> missingAttackDTO = Projections.constructor(
            ClanWarParticipantMissingAttackDTO.class,
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
