package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarMemberQueryRepositoryImpl implements ClanWarMemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ClanWarMemberEntity> findAllByWarId(Long warId) {
        return queryFactory.selectFrom(clanWarMemberEntity)
                           .leftJoin(clanWarMemberEntity.attacks, clanWarMemberAttackEntity).fetchJoin()
                           .where(clanWarMemberEntity.id.warId.eq(warId))
                           .fetch();
    }
}
