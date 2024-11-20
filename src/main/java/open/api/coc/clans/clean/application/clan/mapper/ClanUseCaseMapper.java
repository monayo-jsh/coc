package open.api.coc.clans.clean.application.clan.mapper;

import jakarta.validation.constraints.NotBlank;
import open.api.coc.clans.clean.application.clan.dto.ClanContentUpdateCommand;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.presentation.clan.dto.ClanContentRequest;
import open.api.coc.clans.clean.presentation.clan.dto.ClanResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(
    config = MapStructConfig.class
)
public interface ClanUseCaseMapper {

    ClanResponse toClanResponse(Clan clan);

    default ClanContentUpdateCommand toClanContentUpdateCommand(@NotBlank String clanTag, ClanContentRequest request) {
        return ClanContentUpdateCommand.create(clanTag, request);
    }
}
