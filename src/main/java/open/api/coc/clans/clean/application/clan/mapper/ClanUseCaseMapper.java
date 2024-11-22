package open.api.coc.clans.clean.application.clan.mapper;

import jakarta.validation.constraints.NotBlank;
import open.api.coc.clans.clean.application.clan.dto.ClanContentUpdateCommand;
import open.api.coc.clans.clean.application.clan.dto.ClanQueryCommand;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanMember;
import open.api.coc.clans.clean.presentation.clan.dto.ClanContentRequest;
import open.api.coc.clans.clean.presentation.clan.dto.ClanDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.ClanMemberResponse;
import open.api.coc.clans.clean.presentation.clan.dto.ClanResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface ClanUseCaseMapper {

    default ClanQueryCommand toClanQueryCommand(String type) {
        return ClanQueryCommand.of(type);
    }

    ClanResponse toClanResponse(Clan clan);

    @Mapping(target = "level", source = "clanLevel")
    @Mapping(target = "points", source = "clanPoints")
    @Mapping(target = "joinType", source = "type")
    ClanDetailResponse toClanDetailResponse(Clan latestClan);

    default ClanContentUpdateCommand toClanContentUpdateCommand(@NotBlank String clanTag, ClanContentRequest request) {
        return ClanContentUpdateCommand.create(clanTag, request);
    }

    ClanMemberResponse toClanMemberResponse(ClanMember clanMember);

}
