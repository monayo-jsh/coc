package open.api.coc.clans.clean.domain.clan.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.exception.ClanNotExistsException;
import open.api.coc.clans.clean.domain.clan.external.client.ClanClient;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanContentType;
import open.api.coc.clans.clean.domain.clan.repository.ClanLeagueWarRepository;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanClient clanClient;

    private final ClanRepository clanRepository;
    private final ClanLeagueWarRepository leagueWarRepository;

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
    public List<Clan> findAll() {
        return clanRepository.findAllActiveClans();
    }

    @Transactional
    public void createIfNotExists(String clanTag) {
        if (clanTag == null || clanTag.isEmpty()) return;
        if (clanRepository.exists(clanTag)) return;

        Clan clan = clanClient.findByTag(clanTag);
        clan.deactivate(); // 비활성화 설정

        create(clan);
    }

    public Clan create(Clan clan) {
        clan.changeOrder(getClanMaxOrders()); // 클랜 정렬 순서 설정
        clan.createDefaultContent(); // 컨텐츠 활성화 기본 상태 설정

        return clanRepository.save(clan);
    }

    public Integer getClanMaxOrders() {
        return Optional.ofNullable(clanRepository.selectMaxOrders())
                       .orElse(0);

    }

    public Clan createOrActivate(Clan latestClan) {
        Optional<Clan> existingClan = clanRepository.findById(latestClan.getTag());
        if (existingClan.isPresent()) {
            // 서버에 등록된 클랜인 경우
            Clan clan = existingClan.get();
            clan.activateWithLatestInfo(latestClan);
            return clan;
        }

        // 클랜 생성 기본 설정
        latestClan.initDefault();
        return latestClan;
    }

    public void save(Clan clan) {
        clanRepository.save(clan);
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllByWarType(String warType) {
        // 클랜 목록을 조회한다.
        ClanContentType clanContentType = ClanContentType.ofWartype(warType);
        List<Clan> clans = clanRepository.findAllByClanContentTypeName(clanContentType.name());

        // 리그전 클랜 조회 시 리그전 정보는 현재 시즌 정보로 응답 구성
        if ("league".equalsIgnoreCase(warType)) {
            assignCurrentSeasonLeagueInfo(clans);
        }

        return clans;
    }

    private void assignCurrentSeasonLeagueInfo(List<Clan> clans) {
        if (clans.isEmpty()) return;

        String season = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Map<String, ClanLeagueWarEntity> clanLeagueWarEntityMap = leagueWarRepository.findAllBySeason(season)
                                                                                     .stream()
                                                                                     .collect(Collectors.toMap(ClanLeagueWarEntity::getClanTag, Function.identity()));

        clans.forEach(clan -> {
            ClanLeagueWarEntity leagueWarEntity = clanLeagueWarEntityMap.get(clan.getTag());
            if (leagueWarEntity != null) {
                League warLeague = League.create(leagueWarEntity.getWarLeague());
                clan.changeWarLeague(warLeague);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllByClanContentType(ClanContentType clanContentType) {
        // 클랜 목록을 조회한다.
        return clanRepository.findAllByClanContentTypeName(clanContentType.name());
    }
}
