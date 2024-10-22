package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanGame;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanGameRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanGameEntity;
import open.api.coc.clans.clean.infrastructure.clan.persistence.mapper.ClanGameEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanGameDatabaseService implements ClanGameRepository {

    private final JpaClanGameRepository jpaClanGameRepository;

    private final ClanGameEntityMapper clanGameEntityMapper;

    @Override
    public List<ClanGameDTO> findAllByProgressDate(String progressDate) {
        return jpaClanGameRepository.findAllByProgressDate(progressDate);
    }

    @Override
    public Optional<ClanGame> findByPlayerTagAndProgressDate(String playerTag, String progressDate) {
        return jpaClanGameRepository.findByPlayerTagAndProgressDate(playerTag, progressDate)
                                    .map(clanGameEntityMapper::toClanGame);
    }

    @Override
    public ClanGame save(ClanGame clanGame) {
        ClanGameEntity entity = clanGameEntityMapper.toClanGameEntity(clanGame);
        ClanGameEntity saveEntity = jpaClanGameRepository.save(entity);
        return clanGameEntityMapper.toClanGame(saveEntity);
    }

}
