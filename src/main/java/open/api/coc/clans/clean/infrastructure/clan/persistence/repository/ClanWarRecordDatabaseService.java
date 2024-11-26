package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantRecordDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRecordRepository;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.repository.clan.condition.ClanWarRecordConditionBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarRecordDatabaseService implements ClanWarRecordRepository {

    private final JpaClanWarMemberRecordQueryRepository queryRepository;

    @Override
    public List<ClanWarParticipantRecordDTO> findAll(ClanWarType type, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        ClanWarRecordConditionBuilder condition = new ClanWarRecordConditionBuilder(type, from, to);
        return queryRepository.findAll(condition, pageable);
    }

    @Override
    public List<ClanWarParticipantRecordDTO> findAllByClanTag(String clanTag, ClanWarType type, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        ClanWarRecordConditionBuilder condition = new ClanWarRecordConditionBuilder(type, from, to);
        condition = condition.withClanTag(clanTag);
        return queryRepository.findAll(condition, pageable);
    }
}
