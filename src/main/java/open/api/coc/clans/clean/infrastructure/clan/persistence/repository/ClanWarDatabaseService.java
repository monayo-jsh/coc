package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarDatabaseService implements ClanWarRepository {

    private final JpaClanWarQueryRepository queryRepository;

    private final JpaClanWarMemberQueryRepository memberQueryRepository;

    @Override
    public List<ClanWarDTO> findAllDTOByStartTime(LocalDateTime from, LocalDateTime to) {
        return queryRepository.findAllDTOByStartTime(from, to);
    }

    @Override
    public Optional<ClanWarDTO> findDTOWithAllById(Long warId) {
        return queryRepository.findDTOById(warId).map(this::fetchMemberDTOs);
    }

    private ClanWarDTO fetchMemberDTOs(ClanWarDTO clanWar) {
        // 클랜 참여자 목록
        List<ClanWarMemberDTO> members = memberQueryRepository.findDTOByIdWarId(clanWar.getWarId());

        // 클랜 참여자 매핑
        clanWar.changeMembers(members);

        // 반환
        return clanWar;
    }
}
