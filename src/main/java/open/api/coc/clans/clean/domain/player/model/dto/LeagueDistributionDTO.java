package open.api.coc.clans.clean.domain.player.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;

@Builder(access = AccessLevel.PRIVATE)
public record LeagueDistributionDTO(

    // 리그 정보
    Integer leagueId,

    // 계정 수
    Long count

) {

    public static LeagueDistributionDTO create(Object[] result) {
        Integer leagueId = (Integer) result[0];
        Long count = (Long) result[1];
        return LeagueDistributionDTO.builder()
                                    .leagueId(leagueId)
                                    .count(count)
                                    .build();
    }

}
