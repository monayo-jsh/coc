package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanBadgeEntityMapper;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanContentEntityMapper;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanEntityMapper;
import open.api.coc.clans.database.entity.clan.ClanBadgeEntity;
import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanDatabaseService implements ClanRepository {

    private final JpaClanRepository repository;
    private final JpaClanQueryRepository queryRepository;

    private final ClanEntityMapper clanEntityMapper;
    private final ClanContentEntityMapper contentEntityMapper;
    private final ClanBadgeEntityMapper badgeEntityMapper;

    @Override
    public Optional<Clan> findById(String tag) {
        return queryRepository.findById(tag)
                              .map(clanEntityMapper::toClan);
    }

    @Override
    public List<Clan> findByIds(List<String> clanTags) {
        return queryRepository.findByIds(clanTags)
                              .stream()
                              .map(clanEntityMapper::toClan)
                              .collect(Collectors.toList());
    }

    @Override
    public List<Clan> findAllActiveClans() {
        return queryRepository.findAllActiveClan()
                              .stream()
                              .map(clanEntityMapper::toClan)
                              .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String tag) {
        return repository.existsById(tag);
    }

    @Override
    public List<Clan> findAllByClanContentTypeName(String clanContentTypeName) {
        return queryRepository.findAllByClanContentTypeByName(clanContentTypeName)
                              .stream()
                              .map(clanEntityMapper::toClan)
                              .collect(Collectors.toList());
    }

    @Override
    public Integer selectMaxOrders() {
        return queryRepository.selectMaxOrder();
    }

    @Override
    public Clan save(Clan clan) {
        // 클랜 엔티티 생성
        ClanEntity clanEntity = clanEntityMapper.toClanEntity(clan);
        if (exists(clanEntity.getId())) {
            // 기존 객체 처리
            clanEntity.markNotNew();
        }

        // 클랜 컨텐츠 매핑
        ClanContentEntity contentEntity = contentEntityMapper.toClanContentEntity(clan.getClanContent());
        clanEntity.changeClanContent(contentEntity);

        // 클랜 배지 매핑
        ClanBadgeEntity badgeEntity = badgeEntityMapper.toClanBadgeEntity(clan.getBadgeUrl());
        clanEntity.changeBadgeUrl(badgeEntity);

        ClanEntity saveEntity = repository.save(clanEntity);
        return clanEntityMapper.toClan(saveEntity);
    }
}
