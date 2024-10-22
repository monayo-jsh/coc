package open.api.coc.clans.clean.domain.clan.mapper;

import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.presentation.clan.dto.ClanGameResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClanGameMapper {

    ClanGameResponse toClanGameResponse(String progressDate, Integer completedPoint, List<ClanGameDTO> clanGames);

}
