package open.api.coc.clans.domain.clans.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackEntity;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import open.api.coc.clans.domain.clans.ClanWarMemberAttackResponse;
import open.api.coc.clans.domain.clans.ClanWarMemberResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class EntityClanWarMemberResponseConverter implements Converter<ClanWarMemberEntity, ClanWarMemberResponse> {

    private final EntityClanWarMemberAttackResponseConverter entityClanWarMemberAttackResponseConverter;

    @Override
    public ClanWarMemberResponse convert(ClanWarMemberEntity source) {
        return ClanWarMemberResponse.builder()
                                    .warId(source.getId().getWarId())
                                    .mapPosition(source.getMapPosition())
                                    .tag(source.getId().getTag())
                                    .name(source.getName())
                                    .attacks(makeAttacks(source.getAttacks()))
                                    .build();
    }

    private List<ClanWarMemberAttackResponse> makeAttacks(List<ClanWarMemberAttackEntity> attacks) {
        if (CollectionUtils.isEmpty(attacks)) return Collections.emptyList();
        return attacks.stream()
                      .map(entityClanWarMemberAttackResponseConverter::convert)
                      .collect(Collectors.toList());
    }
}