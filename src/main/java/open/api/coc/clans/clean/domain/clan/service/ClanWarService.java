package open.api.coc.clans.clean.domain.clan.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarRepository;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.springframework.stereotype.Service;

@Service(value = "ClanWarService2")
@RequiredArgsConstructor
public class ClanWarService {

    private final ClanWarRepository clanWarRepository;

    public List<ClanWarEntity> findAll(LocalDate startDate, LocalDate endDate) {
        LocalDateTime from = TimeUtils.withMinTime(startDate);
        LocalDateTime to = TimeUtils.withMaxTime(endDate);

        return clanWarRepository.findAllByStartTime(from, to);
    }
}
