package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarMemberRepository;
import open.api.coc.clans.database.entity.clan.ClanWarMemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarMemberDatabaseService implements ClanWarMemberRepository {

    private final JpaClanWarMemberRepository repository;

    @Override
    public List<ClanWarMemberEntity> findAllById(Long warId) {
        return repository.findByIdWarId(warId);
    }

}
