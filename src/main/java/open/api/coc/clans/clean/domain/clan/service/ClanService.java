package open.api.coc.clans.clean.domain.clan.service;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.exception.ClanNotExistsException;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanRepository clanRepository;


    public void validateExists(String clanTag) {
        if (clanRepository.exists(clanTag)) {
            return;
        }

        throw new ClanNotExistsException(clanTag);
    }
}
