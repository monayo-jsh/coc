package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMemberRankingDTO;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidMemberRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.dto.RaiderRankingDTO;
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
        return raiderEntities.stream()
                             .map(clanCapitalRaidMemberMapper::toClanCapitalRaidMember)
                             .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidMemberRankingDTO> rankingResourceLootedByStartDate(LocalDate startDate) {
        Pageable pageable = Pageable.ofSize(hallOfFameConfig.getRanking());
        List<RaiderRankingDTO> rankingDTOs = clanCapitalRaidMemberRepository.findAllResourceLootedRankingByStartDateAndPage(startDate, pageable);
        return rankingDTOs.stream()
                          .map(clanCapitalRaidMemberMapper::toClanCapitalRaidMemberRankingDTO)
                          .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidMemberRankingDTO> rankingResourceLootedAverageByStartDates(List<LocalDate> startDates) {
        Pageable pageable = Pageable.ofSize(hallOfFameConfig.getRanking());
        List<RaiderRankingDTO> rankingDTOs = clanCapitalRaidMemberRepository.findAllResourceLootedAverageRankingByStartDateAndPage(startDates, pageable);
        return rankingDTOs.stream()
                          .map(clanCapitalRaidMemberMapper::toClanCapitalRaidMemberRankingDTO)
                          .toList();
    }

}
