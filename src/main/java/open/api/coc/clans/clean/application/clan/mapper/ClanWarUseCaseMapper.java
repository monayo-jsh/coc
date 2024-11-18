package open.api.coc.clans.clean.application.clan.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMemberQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberDTO;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberGetRequest;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Mapper(
    config = MapStructConfig.class
)
public abstract class ClanWarUseCaseMapper {

    @Autowired
    private TimeConverter timeConverter;

    public ClanWarQuery toClanWarQuery(Long startDate, Long endDate) {
        return ClanWarQuery.create(mapToLocalDate(startDate), mapToLocalDate(endDate));
    }

    public ClanWarMemberQuery toClanWarMemberQuery(ClanWarMemberGetRequest request) {
        return ClanWarMemberQuery.create(request.clanTag(), mapToLocalDateTime(request.startTime()), mapToYnType(request.necessaryAttackYn()));
    }

    public abstract ClanWarResponse toClanWarResponse(ClanWarDTO clanWar);

    public abstract ClanWarDetailResponse toClanWarDetailResponse(ClanWarDTO clanWar);

    public abstract ClanWarMemberResponse toClanWarMemberResponse(ClanWarMemberDTO clanWarMemberDTO);

    protected LocalDate mapToLocalDate(Long time) {
        return timeConverter.toLocalDate(time);
    }
    protected LocalDateTime mapToLocalDateTime(Long time) { return timeConverter.toLocalDateTime(time); }
    protected YnType mapToYnType(String yn) {
        if (!StringUtils.hasText(yn)) return null;
        return YnType.valueOf(yn.toUpperCase());
    }

    protected long map(LocalDateTime time) {
        return timeConverter.toEpochMilliSecond(time);
    }

}
