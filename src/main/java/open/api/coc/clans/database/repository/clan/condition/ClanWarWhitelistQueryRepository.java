package open.api.coc.clans.database.repository.clan.condition;

import static open.api.coc.clans.database.entity.clan.QClanWarWhitelistEntity.clanWarWhitelistEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanWarWhitelistEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarWhitelistQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Set<String> findAll() {
        return queryFactory.select(clanWarWhitelistEntity)
                           .from(clanWarWhitelistEntity)
                           .fetch()
                           .stream()
                           .map(ClanWarWhitelistEntity::getPlayerTag)
                           .collect(Collectors.toSet());
    }

}
