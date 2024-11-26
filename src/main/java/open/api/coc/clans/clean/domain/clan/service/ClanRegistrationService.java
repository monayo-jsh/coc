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
public class ClanRegistrationService {

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
            clan.activate(); // 활성화 설정
            clan.changeLatestInfo(latestClan); // 현행화
            return clan;
        }

        // 클랜 생성 기본 설정
        latestClan.initDefault();
        return latestClan;
    }

    public void save(Clan clan) {
        clanRepository.save(clan);
    }

    public void synchronizeIfExists(Clan latestClan) {
        clanRepository.findById(latestClan.getTag())
                      .ifPresent(existingClan -> {
                          existingClan.changeLatestInfo(latestClan);
                          clanRepository.save(existingClan);
                      });
    }
}
