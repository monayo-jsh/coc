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
        return mapToClanCapitalRaidMember(raiderEntities);
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

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidMember> findByTag(String playerTag, Integer limit) {
        List<RaiderEntity> raiderEntities = clanCapitalRaidMemberRepository.findAllByPlayerTag(playerTag, limit);
        return mapToClanCapitalRaidMember(raiderEntities);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidMember> findByName(String playerName, Integer limit) {
        List<RaiderEntity> raiderEntities = clanCapitalRaidMemberRepository.findAllByPlayerName(playerName, limit);
        return mapToClanCapitalRaidMember(raiderEntities);
    }

    private List<ClanCapitalRaidMember> mapToClanCapitalRaidMember(List<RaiderEntity> raiderEntities) {
        return raiderEntities.stream()
                             .map(clanCapitalRaidMemberMapper::toClanCapitalRaidMember)
                             .toList();
    }

}
