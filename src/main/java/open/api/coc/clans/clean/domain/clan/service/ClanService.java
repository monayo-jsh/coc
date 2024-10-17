package open.api.coc.clans.clean.domain.clan.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.exception.ClanNotExistsException;
import open.api.coc.clans.clean.domain.clan.external.client.ClanClient;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanEntityMapper;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanClient clanClient;

    private final ClanRepository clanRepository;
    private final ClanEntityMapper clanMapper;

    public void validateClanExists(String clanTag) {
        if (clanRepository.exists(clanTag)) {
            return;
        }

        throw new ClanNotExistsException(clanTag);
    }

    @Transactional(readOnly = true)
    public Clan findById(String clanTag) {
        return clanRepository.findById(clanTag)
                             .map(clanMapper::toClan)
                             .orElseThrow(() -> new ClanNotExistsException(clanTag));
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllByIds(List<String> clanTags) {
        List<ClanEntity> clanEntities = clanRepository.findByIds(clanTags);
        return toDomains(clanEntities);
    }

    @Transactional(readOnly = true)
    public Map<String, Clan> findAllMapByIds(List<String> clanTags) {
        return findAllByIds(clanTags).stream()
                                     .collect(Collectors.toMap(Clan::getTag, clan -> clan));
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllActiveCapitalClans() {
        List<ClanEntity> clanEntities = clanRepository.findAllActiveCapitalClans();
        return toDomains(clanEntities);
    }

    private List<Clan> toDomains(List<ClanEntity> clanEntities) {
        return clanEntities.stream()
                           .map(clanMapper::toClan)
                           .toList();
    }

    @Transactional
    public void createIfNotExists(String clanTag) {
        if (clanTag == null || clanTag.isEmpty()) return;
        if (clanRepository.exists(clanTag)) return;

        create(clanTag);
    }

    @Transactional
    public Clan create(String clanTag) {
        Clan clan = clanClient.findByTag(clanTag);

        clan.changeOrder(getClanMaxOrders()); // 클랜 정렬 순서 설정
        clan.createDefaultContent(); // 컨텐츠 활성화 기본 상태 설정

        return clanRepository.save(clan);
    }

    public Integer getClanMaxOrders() {
        return Optional.ofNullable(clanRepository.selectMaxOrders())
                       .orElse(0);

    }
}
