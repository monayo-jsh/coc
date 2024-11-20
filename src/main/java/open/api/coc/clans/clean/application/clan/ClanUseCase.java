package open.api.coc.clans.clean.application.clan;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.mapper.ClanUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.presentation.clan.dto.ClanResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanUseCase {

    private final ClanService clanService;
    private final ClanUseCaseMapper clanUseCaseMapper;

    @Transactional(readOnly = true)
    public List<ClanResponse> getClans() {
        // 활성화된 클랜 목록을 조회한다.
        List<Clan> clans = clanService.findAllActiveClans();

        // 응답
        return clans.stream()
                    .map(clanUseCaseMapper::toClanResponse)
                    .toList();
    }

    @Transactional
    public ClanResponse registerClan(String clanTag) {
        // 클랜을 등록하거나 활성화한다.
        Clan savedClan = clanService.createOrActivate(clanTag);

        // 응답
        return clanUseCaseMapper.toClanResponse(savedClan);
    }

    @Transactional
    public void deleteClan(String clanTag) {
        clanService.delete(clanTag);
    }
}
