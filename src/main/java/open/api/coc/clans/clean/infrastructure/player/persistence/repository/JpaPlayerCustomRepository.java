package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaPlayerCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<String> findAllPlayerTag() {
        return queryFactory.select(playerEntity.playerTag)
                           .from(playerEntity)
                           .fetch();
    }

    public List<PlayerEntity> findAll(String accountType) {
        BooleanBuilder condition = new BooleanBuilder();
        if ("support".equals(accountType)) {
            condition.and(playerEntity.supportYn.eq(YnType.Y));
        }

        return queryFactory.select(playerEntity)
                           .from(playerEntity)
                           .where(condition)
                           .fetch();
    }
}
