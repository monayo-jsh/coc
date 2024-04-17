package open.api.coc.clans.domain.clans.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanWarAttackRes;
import open.api.coc.clans.domain.clans.ClanWarMemberRes;
import open.api.coc.external.coc.clan.domain.clan.ClanWarAttack;
import open.api.coc.external.coc.clan.domain.clan.ClanWarMember;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class ClanWarMemberResConverter implements Converter<ClanWarMember, ClanWarMemberRes> {

    private final ClanWarAttackResConverter clanWarAttackResConverter;

    @Override
    public ClanWarMemberRes convert(ClanWarMember source) {
        ClanWarAttackRes clanWarAttackRes = null;
        if (!ObjectUtils.isEmpty(source.getBestOpponentAttack())) {
            clanWarAttackRes = clanWarAttackResConverter.convert(source.getBestOpponentAttack());
        }

        return ClanWarMemberRes.builder()
                               .name(source.getName())
                               .tag(source.getTag())
                               .townHallLevel(source.getTownhallLevel())
                               .mapPosition(source.getMapPosition())
                               .attacks(makeAttacks(source.getAttacks()))
                               .bestOpponentAttack(clanWarAttackRes)
                               .build();
    }

    private List<ClanWarAttackRes> makeAttacks(List<ClanWarAttack> attacks) {
        if (CollectionUtils.isEmpty(attacks)) return Collections.emptyList();
        return attacks.stream()
                      .map(clanWarAttackResConverter::convert)
                      .collect(Collectors.toList());
    }

}