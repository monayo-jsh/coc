package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberAttackDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberDTO;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanWarMemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarMemberDTO> findDTOByIdWarId(Long warId) {
        ConstructorExpression<ClanWarMemberAttackDTO> memberAttackDTO = Projections.constructor(ClanWarMemberAttackDTO.class,
                                                                                                clanWarMemberAttackEntity.id.warId,
                                                                                                clanWarMemberAttackEntity.id.order,
                                                                                                clanWarMemberAttackEntity.id.tag,
                                                                                                clanWarMemberAttackEntity.stars,
                                                                                                clanWarMemberAttackEntity.destructionPercentage,
                                                                                                clanWarMemberAttackEntity.duration);

        ConstructorExpression<ClanWarMemberDTO> memberDTO = Projections.constructor(ClanWarMemberDTO.class,
                                                                                    clanWarMemberEntity.id.warId,
                                                                                    clanWarMemberEntity.mapPosition,
                                                                                    clanWarMemberEntity.id.tag,
                                                                                    clanWarMemberEntity.name,
                                                                                    clanWarMemberEntity.necessaryAttackYn,
                                                                                    Projections.list(memberAttackDTO));

        return queryFactory.select(memberDTO)
                           .from(clanWarMemberEntity)
                           .leftJoin(clanWarMemberEntity.attacks, clanWarMemberAttackEntity)
                           .where(clanWarMemberEntity.id.warId.eq(warId))
                           .fetch();
    }

}
