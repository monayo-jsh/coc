package open.api.coc.clans.domain.raid.conveter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.players.PlayerClanResponse;
import open.api.coc.clans.domain.players.converter.PlayerClanResponseConverter;
import open.api.coc.clans.domain.raid.RaidScoreResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class RaidScoreResponseConverter implements Converter<RaiderEntity, RaidScoreResponse> {

    private final PlayerClanResponseConverter playerClanResponseConverter;

    private final TimeConverter timeConverter;

    @Override
    public @NonNull RaidScoreResponse convert(RaiderEntity source) {
        return RaidScoreResponse.builder()
                                .name(source.getName())
                                .tag(source.getTag())
                                .attacks(source.getAttacks())
                                .resourceLooted(source.getResourceLooted())
                                .clan(makeClanResponse(source.getRaid()))
                                .seasonStartDate(timeConverter.toEpochMilliSecond(source.getRaid().getStartDate()))
                                .seasonEndDate(timeConverter.toEpochMilliSecond(source.getRaid().getEndDate()))
                                .build();
    }

    private PlayerClanResponse makeClanResponse(RaidEntity raid) {
        if (ObjectUtils.isEmpty(raid.getClan())) return null;
        return playerClanResponseConverter.convert(raid.getClan());
    }
}
