package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRepository;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarDatabaseService implements ClanWarRepository {

    private final JpaClanWarQueryRepository queryRepository;

    @Override
    public List<ClanWarEntity> findAllByStartTime(LocalDateTime from, LocalDateTime to) {
        return queryRepository.findAllByStartTime(from, to);
    }

}
