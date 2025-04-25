package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantRecordDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipationStatusRecordDTO;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarParticipationRecordSearchCriteria;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRecordRepository;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarParticipationStatusRecordResponse;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.repository.clan.condition.ClanWarParticipationStatusRecordConditionBuilder;
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

    @Override
    public List<ClanWarParticipationStatusRecordDTO> findParticipationRecords(ClanWarParticipationRecordSearchCriteria criteria) {
        ClanWarParticipationStatusRecordConditionBuilder condition = new ClanWarParticipationStatusRecordConditionBuilder(criteria.preparationStartTime(), criteria.preparationEndTime());
        if (criteria.hasPlayerTag()) {
            condition = condition.withPlayerTag(criteria.playerTag());
        }
        if (criteria.hasPlayerName()) {
            condition = condition.withPlayerName(criteria.playerName());
        }
        return queryRepository.findParticipationRecords(condition);
    }
}
