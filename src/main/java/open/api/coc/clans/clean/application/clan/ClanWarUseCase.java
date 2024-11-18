package open.api.coc.clans.clean.application.clan;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.application.clan.mapper.ClanWarUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.domain.clan.service.ClanWarService;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanWarUseCase {

    private final ClanWarService clanWarService;
    private final ClanService clanService;

    private final ClanWarUseCaseMapper clanWarUseCaseMapper;

    @Transactional(readOnly = true)
    public List<ClanWarResponse> getClanWarsFromServer(ClanWarQuery query) {
        // 클랜 전쟁 목록을 조회한다.
        List<ClanWarDTO> clanWars = clanWarService.findAllDTO(query.startDate(), query.endDate());

        // 응답
        return clanWars.stream()
                       .map(clanWarUseCaseMapper::toClanWarResponse)
                       .toList();
    }

    @Transactional(readOnly = true)
    public ClanWarDetailResponse getClanWarDetail(Long warId) {
        // 클랜 전쟁 정보를 조회한다.
        ClanWarDTO clanWar = clanWarService.findDTOWithAllByIdOrThrow(warId);

        // 응답
        return clanWarUseCaseMapper.toClanWarDetailResponse(clanWar);
    }

}
