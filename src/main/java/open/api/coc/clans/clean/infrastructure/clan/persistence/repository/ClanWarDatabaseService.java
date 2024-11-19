package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.dto.LeagueWarRoundCountDTO;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarDatabaseService implements ClanWarRepository {

    private final JpaClanWarRepository repository;
    private final JpaClanWarQueryRepository queryRepository;

    private final JpaClanWarMemberQueryRepository memberQueryRepository;

    @Override
    public Optional<ClanWarEntity> findById(Long warId) {
        return queryRepository.findById(warId);
    }

    @Override
    public List<ClanWarDTO> findAllDTOByStartTime(LocalDateTime from, LocalDateTime to) {
        return queryRepository.findAllDTOByStartTime(from, to);
    }

    @Override
    public Optional<ClanWarDTO> findDTOWithAllById(Long warId) {
        return queryRepository.findDTOById(warId).map(this::fetchMemberDTOs);
    }

    @Override
    public Optional<ClanWarDTO> findDTOWithAllByClanTagAndStartTime(String clanTag, LocalDateTime startTime) {
        return queryRepository.findDTOByClanTagAndStartTime(clanTag, startTime).map(this::fetchMemberDTOs);
    }

    private ClanWarDTO fetchMemberDTOs(ClanWarDTO clanWar) {
        // 클랜 참여자 목록
        List<ClanWarMemberDTO> members = memberQueryRepository.findDTOByIdWarId(clanWar.getWarId());

        // 클랜 참여자 매핑
        clanWar.changeMembers(members);

        // 반환
        return clanWar;
    }

    @Override
    public ClanWarEntity save(ClanWarEntity clanWar) {
        return repository.save(clanWar);
    }

    public Map<String, Integer> findLeagueWarRoundCountMap(LocalDateTime from, LocalDateTime to) {
        List<LeagueWarRoundCountDTO> leagueWarRoundCounts = queryRepository.findLeagueWarRoundCounts(from, to);

        return leagueWarRoundCounts.stream()
                                   .collect(Collectors.toMap(LeagueWarRoundCountDTO::clanTag,
                                                             LeagueWarRoundCountDTO::roundCount));

    }

}
