package open.api.coc.clans.clean.domain.capital.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidMemberRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMemberMapper;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClanCapitalMemberService {

    private final ClanCapitalRaidMemberRepository clanCapitalRaidMemberRepository;

    private final ClanCapitalRaidMemberMapper clanCapitalRaidMemberMapper;

    public List<ClanCapitalRaidMember> findByRaidId(Long raidId) {
        List<RaiderEntity> raiderEntities = clanCapitalRaidMemberRepository.findAllByRaidId(raidId);
        return raiderEntities.stream()
                             .map(clanCapitalRaidMemberMapper::toDomain)
                             .toList();
    }
}
