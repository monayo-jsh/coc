package open.api.coc.clans.domain.clans.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.domain.clans.ClanWarMemberResponse;
import open.api.coc.clans.domain.clans.ClanWarResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class EntityClanWarResponseConverter implements Converter<ClanWarEntity, ClanWarResponse> {

    private final TimeConverter timeConverter;
    private final EntityClanWarMemberResponseConverter entityClanWarMemberResponseConverter;

    public @NonNull ClanWarResponse convertWithMember(ClanWarEntity source) {
        ClanWarResponse clanWarResponse = convert(source);
        clanWarResponse.setMembers(makeClanWarMemberResponse(source.getMembers()));
        return clanWarResponse;
    }

    @Override
    public @NonNull ClanWarResponse convert(ClanWarEntity source) {
        String clanName = "";
        if (Objects.nonNull(source.getClan())) {
            clanName = source.getClan().getName();
        }

        return ClanWarResponse.builder()
                              .warId(source.getWarId())
                              .type(source.getType())
                              .clanTag(source.getClanTag())
                              .clanName(clanName)
                              .state(source.getState())
                              .battleType(source.getBattleType())
                              .teamSize(source.getTeamSize())
                              .attacksPerMember(source.getAttacksPerMember())
                              .startTime(timeConverter.toEpochMilliSecond(source.getStartTime()))
                              .endTime(timeConverter.toEpochMilliSecond(source.getEndTime()))
                              .members(new ArrayList<>())
                              .build();
    }

    private List<ClanWarMemberResponse> makeClanWarMemberResponse(List<ClanWarMemberEntity> members) {
        if (CollectionUtils.isEmpty(members)) return Collections.emptyList();

        return members.stream()
                      .map(entityClanWarMemberResponseConverter::convert)
                      .collect(Collectors.toList());
    }
}