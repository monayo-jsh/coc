package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaiderEntity;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMapper;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMemberMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanCapitalRaidCoreRepository implements ClanCapitalRaidRepository {

    private final JpaRaidRepository jpaRaidRepository;
    private final JpaRaidCustomRepository jpaRaidCustomRepository;

    private final ClanCapitalRaidMapper clanCapitalRaidMapper;
    private final ClanCapitalRaidMemberMapper clanCapitalRaidMemberMapper;

    @Override
    public Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDate startDate) {
        Optional<RaidEntity> findRaidEntity = jpaRaidCustomRepository.findByClanTagAndStartDate(clanTag, startDate);
        return findRaidEntity.map(clanCapitalRaidMapper::toClanCapitalRaidWithMembers);

    }

    @Override
    public List<ClanCapitalRaid> findAllByIds(List<Long> raidIds) {
        return jpaRaidRepository.findAllById(raidIds)
                                .stream()
                                .map(clanCapitalRaidMapper::toClanCapitalRaid)
                                .toList();
    }

    @Override
    public ClanCapitalRaid save(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toRaidEntity(clanCapitalRaid);
        List<RaiderEntity> raiderEntities = clanCapitalRaid.getMembers().stream().map(clanCapitalRaidMemberMapper::toRaiderEntity).toList();
        raidEntity.changeRaiders(raiderEntities);

        RaidEntity saveRaidEntity = jpaRaidRepository.save(raidEntity);
        return clanCapitalRaidMapper.toClanCapitalRaidWithMembers(saveRaidEntity);
    }

    @Override
    public LocalDate findLatestStartDate() {
        return jpaRaidCustomRepository.findLatestStartDate();
    }

    @Override
    public List<LocalDate> findAllLatestStartDate(Pageable pageable) {
        return jpaRaidCustomRepository.findAllLatestStartDateByPage(pageable);
    }

    @Override
    public List<ClanCapitalRaid> findAllWithRaiderByStartDate(LocalDate startDate) {
        return jpaRaidCustomRepository.findAllWithRaiderByStartDate(startDate)
                                      .stream()
                                      .map(clanCapitalRaidMapper::toClanCapitalRaidWithMembers)
                                      .toList();
    }

}
