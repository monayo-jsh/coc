package open.api.coc.clans.clean.application.player.mapper;

import open.api.coc.clans.clean.application.player.model.PlayerListSearchQuery;
import open.api.coc.clans.clean.application.player.model.PlayerSupportUpdateBulkCommand;
import open.api.coc.clans.clean.application.player.model.PlayerSupportUpdateCommand;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.league.mapper.LeagueMapper;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationDTO;
import open.api.coc.clans.clean.presentation.common.dto.RankingHallOfFameResponse;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import open.api.coc.clans.clean.presentation.player.dto.PlayerSupportUpdateRequest;
import open.api.coc.clans.clean.presentation.player.dto.RankingHallOfFameDonationResponse;
import open.api.coc.clans.domain.players.SupportPlayerBulkRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        LeagueMapper.class
    }
)
public interface PlayerUseCaseMapper {

    @Mapping(target = "tag", source = "player.tag")
    @Mapping(target = "name", source = "player.name")
    @Mapping(target = "clan", source = "clan")
    @Mapping(target = "league", source = "league")
    PlayerResponse toPlayerResponse(Player player, Clan clan, League league);

    @Mapping(target = "playerTag", source = "playerTag")
    @Mapping(target = "supportYn", source = "request.supportYn")
    PlayerSupportUpdateCommand toSupportUpdateCommand(String playerTag, PlayerSupportUpdateRequest request);

    @Mapping(target = "score", source = "trophies")
    RankingHallOfFameResponse toRankingTrophiesResponse(Player player);

    @Mapping(target = "score", source = "attackWins")
    RankingHallOfFameResponse toRankingAttackWinsResponse(Player player);

    @Mapping(target = "score", source = "count")
    RankingHallOfFameDonationResponse toRankingDonationResponse(PlayerDonationDTO playerDonationDTO);

    // 플레이어 목록 검색 쿼리
    PlayerListSearchQuery toPlayerListSearchQuery(String accountType, String viewMode, String name);

    // 지원계정 설정 벌크 커맨드
    PlayerSupportUpdateBulkCommand toSupportUpdateBulkCommand(SupportPlayerBulkRequest request);
}