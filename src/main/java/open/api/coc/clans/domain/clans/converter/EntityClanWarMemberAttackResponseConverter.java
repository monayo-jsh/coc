package open.api.coc.clans.domain.clans.converter;

import open.api.coc.clans.database.entity.clan.ClanWarMemberAttackEntity;
import open.api.coc.clans.domain.clans.ClanWarAttackRes;
import open.api.coc.clans.domain.clans.ClanWarMemberAttackResponse;
import open.api.coc.external.coc.clan.domain.clan.ClanWarAttack;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityClanWarMemberAttackResponseConverter implements Converter<ClanWarMemberAttackEntity, ClanWarMemberAttackResponse> {

    @Override
    public ClanWarMemberAttackResponse convert(ClanWarMemberAttackEntity source) {
        return ClanWarMemberAttackResponse.builder()
                                          .warId(source.getId().getWarId())
                                          .tag(source.getId().getTag())
                                          .order(source.getId().getOrder())
                                          .stars(source.getStars())
                                          .destructionPercentage(source.getDestructionPercentage())
                                          .duration(source.getDuration())
                                          .build();
    }

}