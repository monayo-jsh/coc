package open.api.coc.clans.clean.domain.clan.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarMemberAttackRepository;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClanWarMemberService {

    private final ClanWarMemberAttackRepository memberAttackRepository;

    public List<ClanWarMemberMissingAttackDTO> findMissingAttacks(LocalDate startDate, LocalDate endDate) {
        LocalDateTime from = TimeUtils.withMinTime(startDate);
        LocalDateTime to = TimeUtils.withMaxTime(endDate);

        return memberAttackRepository.findMissingAttackPlayers(from, to);
    }
}
