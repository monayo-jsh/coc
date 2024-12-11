package open.api.coc.clans.domain.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaiderEntity;
import open.api.coc.clans.domain.clans.ClanResponse;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class AdminRaidResponse{
    private List<ClanResponse> clanResponses;
    private List<RaiderResponse> raiderResponse;

    public static AdminRaidResponse toResponse(List<ClanResponse> clanResponse, List<RaiderEntity> raiderEntityList) {
        return new AdminRaidResponse(clanResponse, raiderEntityList.stream().map(RaiderResponse::toResponse)
                .collect(Collectors.toList()));
    }
}
