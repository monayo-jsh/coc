package open.api.coc.clans.clean.domain.competition.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionClanScheduleNotExistsException;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClanSchedule;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionClanScheduleRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionClanScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionClanScheduleService {

    private final CompetitionClanScheduleRepository clanScheduleRepository;
    private final CompetitionClanScheduleMapper clanScheduleMapper;

    @Transactional(readOnly = true)
    public List<CompetitionClanSchedule> findAllByCompClanId(Long compClanId) {
        List<CompetitionClanScheduleEntity> clanScheduleEntities = clanScheduleRepository.findAllByCompClanId(compClanId);
        return clanScheduleEntities.stream()
                                              .map(clanScheduleMapper::toDomain)
                                              .toList();
    }

    @Transactional
    public void create(CompetitionClanSchedule competitionClanSchedule) {
        CompetitionClanScheduleEntity entity = clanScheduleMapper.toEntity(competitionClanSchedule);
        clanScheduleRepository.save(entity);
    }

    @Transactional
    public void remove(Long clanScheduleId, Long compClanId) {
        // 참여 클랜 라운드 일정 조회
        CompetitionClanScheduleEntity clanScheduleEntity = clanScheduleRepository.findById(clanScheduleId)
                                                                                 .orElseThrow(() -> new CompetitionClanScheduleNotExistsException(clanScheduleId));

        // 도메인 객체 변환
        CompetitionClanSchedule clanSchedule = clanScheduleMapper.toDomain(clanScheduleEntity);

        // 참여 클랜 라운드 일정 데이터 검증
        if (clanSchedule.isNotEqualsCompClanId(compClanId)) {
            throw new CompetitionClanScheduleNotExistsException(clanScheduleId);
        }

        // 참여 클랜 라운드 일정 삭제
        clanScheduleRepository.deleteById(clanScheduleId);
    }

}
