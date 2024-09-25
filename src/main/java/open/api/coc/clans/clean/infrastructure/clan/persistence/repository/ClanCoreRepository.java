package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanRepository;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanCoreRepository implements ClanRepository {

    private final JpaClanRepository jpaClanRepository;
    private final JpaClanCustomRepository jpaClanCustomRepository;

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
}
