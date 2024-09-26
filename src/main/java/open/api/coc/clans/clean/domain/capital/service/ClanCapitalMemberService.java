package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidMemberRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMemberMapper;
import open.api.coc.clans.common.config.HallOfFameConfig;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanCapitalMemberService {

    private final HallOfFameConfig hallOfFameConfig;

    private final ClanCapitalRaidMemberRepository clanCapitalRaidMemberRepository;

    private final ClanCapitalRaidMemberMapper clanCapitalRaidMemberMapper;

    public List<ClanCapitalRaidMember> findByRaidId(Long raidId) {
        List<RaiderEntity> raiderEntities = clanCapitalRaidMemberRepository.findAllByRaidId(raidId);
        return toDomains(raiderEntities);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidMember> rankingResourceLootedByStartDate(LocalDate startDate) {
        Pageable pageable = Pageable.ofSize(hallOfFameConfig.getRanking());
        List<RaiderEntity> raiderEntities = clanCapitalRaidMemberRepository.findAllResourceLootedRankingByStartDateAndPage(startDate, pageable);
        return toDomains(raiderEntities);

    }

    private List<ClanCapitalRaidMember> toDomains(List<RaiderEntity> raiderEntities) {
        return raiderEntities.stream()
                             .map(clanCapitalRaidMemberMapper::toDomain)
                             .toList();
    }
}
