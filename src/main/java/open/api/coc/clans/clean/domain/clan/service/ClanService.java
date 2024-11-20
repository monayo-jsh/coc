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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanClient clanClient;
    private final ClanRepository clanRepository;

    @Transactional(readOnly = true)
    public Clan findById(String clanTag) {
        if (clanTag == null || clanTag.isEmpty()) {
            throw new IllegalArgumentException("clanTag can not be null");
        }

        return clanRepository.findById(clanTag)
                             .orElseThrow(() -> new ClanNotExistsException(clanTag));
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllByIds(List<String> clanTags) {
        if (clanTags == null || clanTags.isEmpty()) {
            throw new IllegalArgumentException("clanTags is empty");
        }

        return clanRepository.findByIds(clanTags);
    }

    @Transactional(readOnly = true)
    public Map<String, Clan> findAllMapByIds(List<String> clanTags) {
        return findAllByIds(clanTags).stream()
                                     .collect(Collectors.toMap(Clan::getTag, clan -> clan));
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllActiveClans() {
        return clanRepository.findAllActiveClans();
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllActiveCapitalClans() {
        return clanRepository.findAllActiveCapitalClans();
    }

    @Transactional
    public void createIfNotExists(String clanTag) {
        if (clanTag == null || clanTag.isEmpty()) return;
        if (clanRepository.exists(clanTag)) return;

        Clan clan = clanClient.findByTag(clanTag);
        clan.deactivate(); // 비활성화 상태로 생성
        create(clan);
    }

    @Transactional
    public Clan create(Clan clan) {
        clan.changeOrder(getClanMaxOrders()); // 클랜 정렬 순서 설정
        clan.createDefaultContent(); // 컨텐츠 활성화 기본 상태 설정

        return clanRepository.save(clan);
    }

    public Integer getClanMaxOrders() {
        return Optional.ofNullable(clanRepository.selectMaxOrders())
                       .orElse(0);

    }

    @Transactional
    public Clan createOrActivate(String clanTag) {
        // 클랜 최신 정보를 조회한다.
        Clan latestClan = clanClient.findByTag(clanTag);

        // 활성화 상태로 설정
        latestClan.activate();

        // 클랜 정보를 생성하거나 활성화한다.
        return clanRepository.findById(clanTag)
                             .map(existingClan -> update(existingClan, latestClan))
                             .orElseGet(() -> create(latestClan));
    }

    private Clan update(Clan existingClan, Clan latestClan) {
        // 클랜 리그 정보 갱신
        existingClan.changeWarLeague(latestClan.getWarLeague());
        // 캐피탈 홀 레벨 갱신
        existingClan.changeClanCapital(latestClan.getClanCapital());
        // 캐피탈 트로피 점수 갱신
        existingClan.changeCapitalPoints(latestClan.getClanCapitalPoints());
        // 캐피탈 리그 갱신
        existingClan.changeCapitalLeague(latestClan.getCapitalLeague());

        // 클랜 활성화
        existingClan.activate();

        clanRepository.save(existingClan);
        return existingClan;
    }

    @Transactional
    public void delete(String clanTag) {
        // 클랜을 조회한다.
        Clan clan = clanRepository.findById(clanTag)
                                  .orElseThrow(() -> new ClanNotExistsException(clanTag));

        // 클랜을 비활성화한다.
        clan.deactivate();

        // 클랜 정보를 저장한다.
        clanRepository.save(clan);
    }
}
