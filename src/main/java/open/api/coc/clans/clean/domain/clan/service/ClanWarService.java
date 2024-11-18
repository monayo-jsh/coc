package open.api.coc.clans.clean.domain.clan.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.exception.ClanWarNotExistsException;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRepository;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ClanWarService2")
@RequiredArgsConstructor
public class ClanWarService {

    private final ClanWarRepository clanWarRepository;

    public ClanWarEntity findByIdOrThrow(Long warId) {
        return clanWarRepository.findById(warId).orElseThrow(() -> new ClanWarNotExistsException(warId));
    }
    @Transactional(readOnly = true)
    public List<ClanWarDTO> findAllDTO(LocalDate startDate, LocalDate endDate) {
        LocalDateTime from = TimeUtils.withMinTime(startDate);
        LocalDateTime to = TimeUtils.withMaxTime(endDate);

        return clanWarRepository.findAllDTOByStartTime(from, to);
    }

    @Transactional(readOnly = true)
    public ClanWarDTO findDTOWithAllByIdOrThrow(Long warId) {
        return clanWarRepository.findDTOWithAllById(warId).orElseThrow(() -> new ClanWarNotExistsException(warId));
    }

    @Transactional(readOnly = true)
    public ClanWarDTO findDTOWithAllByClanTagAndStartTimeOrThrow(String clanTag, LocalDateTime startTime) {
        return clanWarRepository.findDTOWithAllByClanTagAndStartTime(clanTag, startTime).orElseThrow(() -> new ClanWarNotExistsException(clanTag, startTime));
    }

    @Transactional
    public void save(ClanWarEntity clanWar) {
        clanWarRepository.save(clanWar);
    }

}
