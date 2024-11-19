package open.api.coc.clans.clean.domain.clan.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMissingAttackSearchCriteria;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarMemberAttackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanWarMemberService {

    private final ClanWarMemberAttackRepository memberAttackRepository;

    @Transactional(readOnly = true)
    public List<ClanWarMemberMissingAttackDTO> getMissingAttacks(ClanWarMissingAttackSearchCriteria criteria) {
        if (criteria.hasTag()) {
            // 태그 검색
            return memberAttackRepository.findAllByTag(criteria.tag(), criteria.from(), criteria.to());
        }

        if (criteria.hasName()) {
            // 이름 검색
            return memberAttackRepository.findAllByName(criteria.name(), criteria.from(), criteria.to());
        }

        // 기간 검색
        return memberAttackRepository.findAllByPeriod(criteria.from(), criteria.to());
    }
}
