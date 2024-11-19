package open.api.coc.clans.clean.application.clan.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMemberQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMissingAttackPlayerQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMissingAttackQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberMissingAttackResponse;
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

    public ClanWarMemberQuery toClanWarMemberQuery(String clanTag, Long startTime, String necessaryAttackYn) {
        return ClanWarMemberQuery.create(clanTag, mapToLocalDateTime(startTime), mapToYnType(necessaryAttackYn));
    }

    public abstract ClanWarResponse toClanWarResponse(ClanWarDTO clanWar);

    public abstract ClanWarDetailResponse toClanWarDetailResponse(ClanWarDTO clanWar);

    public abstract ClanWarMemberResponse toClanWarMemberResponse(ClanWarMemberDTO clanWarMemberDTO);

    public ClanWarMissingAttackQuery toClanWarMissingAttackQuery(Long startDate, Long endDate) {
        return ClanWarMissingAttackQuery.create(mapToLocalDate(startDate), mapToLocalDate(endDate));
    }

    public ClanWarMissingAttackPlayerQuery toClanWarMissingAttackPlayerQuery(String tag, String name, Integer queryDate) {
        return ClanWarMissingAttackPlayerQuery.create(tag, name, queryDate);
    }

    public abstract ClanWarMemberMissingAttackResponse toClanWarMemberMissingAttackResponse(ClanWarMemberMissingAttackDTO clanWarMemberMissingAttackDTO);

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
