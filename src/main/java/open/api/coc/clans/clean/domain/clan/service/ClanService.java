package open.api.coc.clans.clean.domain.clan.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.exception.ClanNotExistsException;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanMapper;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanRepository clanRepository;
    private final ClanMapper clanMapper;

    public void validateExists(String clanTag) {
        if (clanRepository.exists(clanTag)) {
            return;
        }

        throw new ClanNotExistsException(clanTag);
    }

    @Transactional(readOnly = true)
    public Clan findById(String clanTag) {
        ClanEntity clanEntity = clanRepository.findById(clanTag)
                                              .orElseThrow(() -> new ClanNotExistsException(clanTag));

        return clanMapper.toDomain(clanEntity);
    }

    @Transactional(readOnly = true)
    public List<Clan> findAllByIds(List<String> clanTags) {
        List<ClanEntity> clanEntities = clanRepository.findByIds(clanTags);
        return clanEntities.stream()
                           .map(clanMapper::toDomain)
                           .toList();
    }

    @Transactional(readOnly = true)
    public Map<String, Clan> findAllMapByIds(List<String> clanTags) {
        return findAllByIds(clanTags).stream()
                                     .collect(Collectors.toMap(Clan::getTag, clan -> clan));
    }
}
