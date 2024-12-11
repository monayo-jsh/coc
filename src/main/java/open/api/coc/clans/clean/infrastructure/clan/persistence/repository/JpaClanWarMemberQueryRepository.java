package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantAttackDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantDTO;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanWarMemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarParticipantDTO> findDTOByIdWarId(Long warId) {
        ConstructorExpression<ClanWarParticipantAttackDTO> attackDTO = Projections.constructor(ClanWarParticipantAttackDTO.class,
                                                                                               clanWarMemberAttackEntity.id.warId,
                                                                                               clanWarMemberAttackEntity.id.tag,
                                                                                               clanWarMemberAttackEntity.id.order,
                                                                                               clanWarMemberAttackEntity.stars,
                                                                                               clanWarMemberAttackEntity.destructionPercentage,
                                                                                               clanWarMemberAttackEntity.duration);

        ConstructorExpression<ClanWarParticipantDTO> memberDTO = Projections.constructor(ClanWarParticipantDTO.class,
                                                                                         clanWarMemberEntity.id.warId,
                                                                                         clanWarMemberEntity.id.tag,
                                                                                         clanWarMemberEntity.name,
                                                                                         clanWarMemberEntity.mapPosition,
                                                                                         clanWarMemberEntity.necessaryAttackYn,
                                                                                         Projections.list(attackDTO));

        List<ClanWarParticipantDTO> members = queryFactory.select(memberDTO)
                                                          .from(clanWarMemberEntity)
                                                          .leftJoin(clanWarMemberEntity.attacks, clanWarMemberAttackEntity)
                                                          .where(clanWarMemberEntity.id.warId.eq(warId))
                                                          .fetch();

        Map<String, ClanWarParticipantDTO> memberMap = new HashMap<>();
        for(ClanWarParticipantDTO member : members) {
            if (memberMap.containsKey(member.getTag())) {
                // 중복된 플레이어 처리
                ClanWarParticipantDTO existingMember = memberMap.get(member.getTag());
                existingMember.addAttacks(member.getAttacks());
            } else {
                // 신규 플레이어 처리
                memberMap.put(member.getTag(), member);
            }
        }

        return new ArrayList<>(memberMap.values()).stream()
                                                  .sorted(Comparator.comparing(ClanWarParticipantDTO::getMapPosition))
                                                  .toList();
    }

}
