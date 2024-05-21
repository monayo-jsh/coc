package open.api.coc.clans.service;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.raid.RaidEntity;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.database.entity.raid.converter.RaidEntityConverter;
import open.api.coc.clans.database.repository.raid.RaidRepository;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.ClanResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaidService {
    private final ClansService clansService;
    private final RaidEntityConverter raidEntityConverter;
    private final RaidRepository raidRepository;

    @Transactional
    public void saveFinishedRaidInfo() {
        List<ClanResponse> clanList = clansService.getClanCaptialList();
        for (ClanResponse clan : clanList) {
            ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = clansService.getClanCapitalRaidSeason(clan.getTag());
            raidRepository.save(raidEntityConverter.convert(clan.getTag(), clanCapitalRaidAttacker));
        }
    }

    public List<RaiderEntity> getRaiderWithLessThanPoints(int capitalClanCount, int point) {
        List<RaidEntity> raidEntityList = raidRepository.getLastWeekRaidStatistics(PageRequest.of(0,capitalClanCount));
        List<RaiderEntity> result = new ArrayList<>();
        for (RaidEntity raid : raidEntityList) {
            for (RaiderEntity raider : raid.getRaiderEntityList()) {
                if(raider.getResourceLooted() < point) {
                    result.add(raider);
                }
            }
        }
        return result;
    }
}
