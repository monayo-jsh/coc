package open.api.coc.clans.database.repository.raid;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class RaiderQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final RaiderRepository raiderRepository;

    public List<RaiderEntity> findByTag(String playerTag, Integer limit) {
        if (!StringUtils.hasText(playerTag))
            throw CustomRuntimeException.create(ExceptionCode.INTERNAL_ERROR, "playerTag can not be empty");

        return raiderRepository.findByTag(playerTag, limit);
    }

    public List<RaiderEntity> findByName(String playerName, Integer limit) {
        if (!StringUtils.hasText(playerName))
            throw CustomRuntimeException.create(ExceptionCode.INTERNAL_ERROR, "playerName can not be empty");

        return raiderRepository.findByName(playerName, limit);
    }
}
