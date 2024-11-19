package open.api.coc.clans.clean.domain.clan.mapper;

import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.presentation.clan.dto.game.ClanGameRecordResponse;
import open.api.coc.clans.clean.presentation.clan.dto.game.ClanGameResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClanGameMapper {

    @Mapping(target = "records", source = "clanGames")
    ClanGameResponse toClanGameResponse(String progressDate, Integer completedPoint, List<ClanGameDTO> clanGames);

    @Mapping(target = "tag", source = "playerTag")
    @Mapping(target = "name", source = "playerName")
    @Mapping(target = "achievementDate", source = "lastModifiedAt")
    ClanGameRecordResponse map(ClanGameDTO clanGameDTO);

}
