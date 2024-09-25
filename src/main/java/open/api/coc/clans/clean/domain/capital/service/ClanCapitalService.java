package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanCapitalService {

    private final ClanCapitalRaidRepository clanCapitalRaidRepository;
    private final ClanCapitalRaidMapper clanCapitalRaidMapper;

    public Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDateTime startDateTime) {
        Optional<RaidEntity> findRaidEntity = clanCapitalRaidRepository.findByClanTagAndStartDate(clanTag, startDateTime.toLocalDate());

        if (findRaidEntity.isEmpty()) return Optional.empty();

        ClanCapitalRaid clanCapitalRaid = clanCapitalRaidMapper.toDomain(findRaidEntity.get());
        return Optional.of(clanCapitalRaid);
    }

    @Transactional
    public ClanCapitalRaid create(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toEntity(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);
        return clanCapitalRaidMapper.toDomain(saveRaidEntity);
    }

    @Transactional
    public ClanCapitalRaid update(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toEntity(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);

        return clanCapitalRaidMapper.toDomain(saveRaidEntity);
    }

    @Transactional
    public void updateRaid(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toEntity(clanCapitalRaid);
        clanCapitalRaidRepository.update(raidEntity);
    }
}
