package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidMemberRepository;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanCapitalRaidMemberCoreRepository implements ClanCapitalRaidMemberRepository {

    private final JpaRaiderRepository jpaRaiderRepository;

    public List<RaiderEntity> findAllByRaidId(Long raidId) {
        if (raidId == null) {
            throw new IllegalArgumentException("raidId can not be null");
        }

        return jpaRaiderRepository.findAllByRaidId(raidId);
    }

}
