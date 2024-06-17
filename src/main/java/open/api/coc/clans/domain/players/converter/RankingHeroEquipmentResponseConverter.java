package open.api.coc.clans.domain.players.converter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.player.RankingHeroEquipment;
import open.api.coc.clans.domain.common.HeroEquipmentResponse;
import open.api.coc.clans.domain.common.converter.HeroEquipmentResponseConverter;
import open.api.coc.clans.domain.players.RankingHeroEquipmentResponse;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankingHeroEquipmentResponseConverter implements Converter<RankingHeroEquipment, RankingHeroEquipmentResponse> {

    private final HeroEquipmentResponseConverter heroEquipmentResponseConverter;

    @Override
    public RankingHeroEquipmentResponse convert(RankingHeroEquipment source) {
        return RankingHeroEquipmentResponse.builder()
                                           .heroName(source.getHeroName())
                                           .heroEquipments(makeHeroEquipments(source.getEquipments()))
                                           .count(source.getCount())
                                           .totalCount(source.getTotalCount())
                                           .build();
    }

    private List<HeroEquipmentResponse> makeHeroEquipments(List<String> equipmentNames) {
        return equipmentNames.stream()
                             .map(this::makeHeroEquipment)
                             .sorted(Comparator.comparingInt(HeroEquipmentResponse::getCode).reversed())
                             .collect(Collectors.toList());
    }

    private HeroEquipmentResponse makeHeroEquipment(String heroEquipmentName) {
        return heroEquipmentResponseConverter.convert(new HeroEquipment(heroEquipmentName, "home", 0, 0));
    }
}
