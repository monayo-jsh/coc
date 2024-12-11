package open.api.coc.clans.clean.infrastructure.competition.persistence.mapper;

import open.api.coc.clans.clean.domain.competition.model.CompetitionClan;
import open.api.coc.clans.clean.infrastructure.competition.persistence.dto.CompetitionClanDTO;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompetitionClanMapper {

    @Mapping(target = "competition.id", source = "compId")
    @Mapping(target = "clanTag", source = "tag")
    CompetitionClanEntity toEntity(CompetitionClan competitionClan);

    @Mapping(target = "compId", source = "competition.id")
    @Mapping(target = "tag", source = "clanTag")
    CompetitionClan toDomain(CompetitionClanEntity competitionClanEntity);

    @Mapping(target = "tag", source = "clanTag")
    @Mapping(target = "name", source = "clanName")
    CompetitionClan toDomain(CompetitionClanDTO competitionClanDTO);
}
