package open.api.coc.clans.clean.domain.clan.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantRecordDTO;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMemberRecordSearchCriteria;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRecordRepository;
import open.api.coc.clans.common.config.HallOfFameConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanWarRecordService {

    private final HallOfFameConfig hallOfFameConfig;
    private final ClanWarRecordRepository clanWarRecordRepository;

    @Transactional(readOnly = true)
    public List<ClanWarParticipantRecordDTO> getMemberRecords(ClanWarMemberRecordSearchCriteria criteria) {
        Pageable pageable = criteria.makePageable(hallOfFameConfig.getRanking());
        if (criteria.hasClanTag()) {
            return clanWarRecordRepository.findAllByClanTag(criteria.clanTag(), criteria.clanWarType(), criteria.from(), criteria.to(), pageable);
        }

        return clanWarRecordRepository.findAll(criteria.clanWarType(), criteria.from(), criteria.to(), pageable);
    }
}
