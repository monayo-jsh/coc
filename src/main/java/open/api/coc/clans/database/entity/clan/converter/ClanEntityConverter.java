package open.api.coc.clans.database.entity.clan.converter;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.external.coc.clan.domain.common.PlayerClan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanEntityConverter implements Converter<PlayerClan, ClanEntity> {

    @Override
    public @NonNull ClanEntity convert(PlayerClan source) {
        return ClanEntity.builder()
                         .tag(source.getTag())
                         .name(source.getName())
                         .order(Integer.MAX_VALUE)
                         .visibleYn(YnType.N)
                         .regDate(LocalDateTime.now())
                         .build();
    }

}
