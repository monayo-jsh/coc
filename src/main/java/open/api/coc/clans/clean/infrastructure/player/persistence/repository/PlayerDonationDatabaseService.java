package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.PlayerDonationStat;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationDTO;
import open.api.coc.clans.clean.domain.player.repository.PlayerDonationRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerDonationStatEntityMapper;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerDonationStatEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PlayerDonationDatabaseService implements PlayerDonationRepository {

    private final JpaPlayerDonationStatRepository jpaPlayerDonationStatRepository;
    private final JpaPlayerDonationStatQueryRepository jpaPlayerDonationStatQueryRepository;

    private final PlayerDonationStatEntityMapper playerDonationStatEntityMapper;

    @Override
    @Transactional
    public PlayerDonationStat save(PlayerDonationStat playerDonationStat) {
        PlayerDonationStatEntity entity = playerDonationStatEntityMapper.toPlayerDonationStatEntity(playerDonationStat);
        PlayerDonationStatEntity saveEntity = jpaPlayerDonationStatRepository.save(entity);
        return playerDonationStatEntityMapper.toPlayerDonationStat(saveEntity);
    }

    @Override
    @Transactional
    public Optional<PlayerDonationStat> findByPlayerTagAndSeason(String playerTag, String season) {
        return jpaPlayerDonationStatQueryRepository.findByPlayerTagAndSeason(playerTag, season)
                                                   .map(playerDonationStatEntityMapper::toPlayerDonationStat);
    }

    @Override
    public List<PlayerDonationDTO> findDonationRanking(String season, Integer pageSize) {
        Pageable page = PageRequest.of(0, pageSize);
        return jpaPlayerDonationStatQueryRepository.findRankingDonationsBySeasonAndPage(season, page);
    }

}
