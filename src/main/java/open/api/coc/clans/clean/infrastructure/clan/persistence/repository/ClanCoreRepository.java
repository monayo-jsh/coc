package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import java.util.Optional;
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
public class ClanCoreRepository implements ClanRepository {

    private final JpaClanRepository jpaClanRepository;
    private final JpaClanCustomRepository jpaClanCustomRepository;

    private final ClanEntityMapper clanEntityMapper;
    private final ClanContentEntityMapper contentEntityMapper;
    private final ClanBadgeEntityMapper badgeEntityMapper;

    @Override
    public Optional<ClanEntity> findById(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException("tag can not be null");
        }
        return jpaClanCustomRepository.findById(tag);
    }

    @Override
    public List<ClanEntity> findByIds(List<String> clanTags) {
        if (clanTags == null || clanTags.isEmpty()) {
            throw new IllegalArgumentException("clanTags can not be null or empty");
        }

        return jpaClanCustomRepository.findByIds(clanTags);
    }

    @Override
    public boolean exists(String tag) {
        return jpaClanRepository.existsById(tag);
    }

    @Override
    public List<ClanEntity> findAllActiveCapitalClans() {
        return jpaClanCustomRepository.findAllActiveCapitalClans();
    }

    @Override
    public Integer selectMaxOrders() {
        return jpaClanCustomRepository.selectMaxOrder();
    }

    @Override
    public Clan save(Clan clan) {
        // 클랜 엔티티 생성
        ClanEntity clanEntity = clanEntityMapper.toClanEntity(clan);

        // 클랜 컨텐츠 매핑
        ClanContentEntity contentEntity = contentEntityMapper.toClanContentEntity(clan.getClanContent());
        clanEntity.changeClanContent(contentEntity);

        // 클랜 배지 매핑
        ClanBadgeEntity badgeEntity = badgeEntityMapper.toClanBadgeEntity(clan.getBadgeUrl());
        clanEntity.changeBadgeUrl(badgeEntity);

        ClanEntity saveEntity = jpaClanRepository.save(clanEntity);
        return clanEntityMapper.toClan(saveEntity);
    }
}
