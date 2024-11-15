package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRepository;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarDatabaseService implements ClanWarRepository {

    private final JpaClanWarRepository repository;
    private final JpaClanWarQueryRepository queryRepository;

    private final JpaClanWarMemberRepository memberRepository;

    @Override
    public Optional<ClanWarEntity> findWithAllById(Long warId) {
        return queryRepository.findById(warId).map(this::fetchMembers);
    }

    private ClanWarEntity fetchMembers(ClanWarEntity clanWar) {
        // 클랜 참여자 목록
        List<ClanWarMemberEntity> members = memberRepository.findByIdWarId(clanWar.getWarId());
        // 클랜 참여자 매핑
        clanWar.changeMembers(members);
        // 반환
        return clanWar;
    }

    @Override
    public List<ClanWarEntity> findAllByStartTime(LocalDateTime from, LocalDateTime to) {
        return queryRepository.findAllByStartTime(from, to);
    }

}
