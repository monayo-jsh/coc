package open.api.coc.clans.clean.domain.clan.service;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.exception.ClanNotExistsException;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanMapper;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanRepository clanRepository;
    private final ClanMapper clanMapper;

    public void validateExists(String clanTag) {
        if (clanRepository.exists(clanTag)) {
            return;
        }

        throw new ClanNotExistsException(clanTag);
    }

    public Clan findById(String clanTag) {
        ClanEntity clanEntity = clanRepository.findById(clanTag)
                                              .orElseThrow(() -> new ClanNotExistsException(clanTag));

        return clanMapper.toDomain(clanEntity);
    }
}
