package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

}
