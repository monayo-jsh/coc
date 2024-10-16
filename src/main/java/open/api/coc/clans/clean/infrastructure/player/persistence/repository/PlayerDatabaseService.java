package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.repository.PlayerRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.repository.JpaClanRepository;
import open.api.coc.clans.clean.infrastructure.league.persistence.repository.JpaLeagueRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEquipmentEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerSpellEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerTroopsEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerEntityMapper;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerHeroEntityMapper;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerHeroEquipmentEntityMapper;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerSpellEntityMapper;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerTroopsEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerDatabaseService implements PlayerRepository {

    private final JpaPlayerRepository jpaPlayerRepository;

    private final JpaClanRepository jpaClanRepository;
    private final JpaLeagueRepository jpaLeagueRepository;

    private final PlayerEntityMapper playerEntityMapper;
    private final PlayerHeroEntityMapper heroEntityMapper;
    private final PlayerHeroEquipmentEntityMapper heroEquipmentEntityMapper;
    private final PlayerSpellEntityMapper spellEntityMapper;
    private final PlayerTroopsEntityMapper troopsEntityMapper;

    @Override
    public List<Player> findAll() {
        return jpaPlayerRepository.findAll()
                                  .stream()
                                  .map(playerEntityMapper::toPlayer)
                                  .toList();
    }

    @Override
    public Optional<Player> findById(String playerTag) {
        return jpaPlayerRepository.findById(playerTag)
                                  .map(playerEntityMapper::toPlayer);
    }

    @Override
    public boolean exists(String playerTag) {
        return jpaPlayerRepository.existsById(playerTag);
    }

    @Override
    public Player save(Player newPlayer) {
        // 플레이어 엔티티 생성
        PlayerEntity playerEntity = playerEntityMapper.toEntity(newPlayer);

        // 리그 매핑
        if (newPlayer.getLeagueId() != null) {
            jpaLeagueRepository.findById(newPlayer.getLeagueId())
                               .ifPresent(playerEntity::changeLeague);
        }

        // 클랜 매핑
        if (newPlayer.getClanTag() != null) {
            jpaClanRepository.findById(newPlayer.getClanTag())
                             .ifPresent(playerEntity::changeClan);
        }

        // 플레이어 영웅 매핑
        List<PlayerHeroEntity> heroEntities = newPlayer.getHeroes().stream().map(heroEntityMapper::toPlayerHeroEntity).toList();
        playerEntity.changeHeroes(heroEntities);

        // 플레이어 영웅장비 매핑
        List<PlayerHeroEquipmentEntity> heroEquipmentEntities = newPlayer.getHeroEquipments().stream().map(heroEquipmentEntityMapper::toPlayerHeroEquipmentEntity).toList();
        playerEntity.changeHeroEquipments(heroEquipmentEntities);

        // 플레이어 마법 매핑
        List<PlayerSpellEntity> spellEntities = newPlayer.getSpells().stream().map(spellEntityMapper::toPlayerSpellEntity).toList();
        playerEntity.changeSpells(spellEntities);

        // 플레이어 유닛 매핑
        List<PlayerTroopsEntity> petEntities = newPlayer.getPets().stream().map(troopsEntityMapper::toPlayerPetEntity).toList();
        List<PlayerTroopsEntity> siegeMachineEntities = newPlayer.getSiegeMachines().stream().map(troopsEntityMapper::toPlayerSiegeMachineEntity).toList();
        List<PlayerTroopsEntity> troopsEntities = Stream.of(petEntities, siegeMachineEntities).flatMap(Collection::stream).toList();
        playerEntity.changeTroops(troopsEntities);

        // 플레이어 유무 확인
        boolean isExistsPlayer = jpaPlayerRepository.existsById(newPlayer.getTag());
        if (isExistsPlayer) {
            playerEntity.markNotNew();
        }

        PlayerEntity savePlayerEntity = jpaPlayerRepository.save(playerEntity);
        return playerEntityMapper.toPlayer(savePlayerEntity);
    }

}
