package open.api.coc.clans.clean.domain.competition.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionAlreadyExistsException;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionNotExistsException;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionMapper;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    public void validateExists(String name, LocalDate startDate, LocalDate endDate) {
        if (competitionRepository.exists(name, startDate, endDate)) {
            throw new CompetitionAlreadyExistsException("%s - %s ~ %s".formatted(name,
                                                                                 TimeUtils.formattedISODate(startDate),
                                                                                 TimeUtils.formattedISODate(endDate)));
        }
    }

    @Transactional
    public Competition create(Competition competition) {
        CompetitionEntity competitionEntity = competitionMapper.toEntity(competition);
        CompetitionEntity saveCompetitionEntity = competitionRepository.save(competitionEntity);
        return competitionMapper.toDomain(saveCompetitionEntity);
    }

    public Competition findById(Long id) {
        CompetitionEntity competitionEntity = competitionRepository.findById(id)
                                                                   .orElseThrow(() -> new CompetitionNotExistsException(id));
        return competitionMapper.toDomain(competitionEntity);
    }

    @Transactional
    public void update(Competition competition) {
        CompetitionEntity competitionEntity = competitionMapper.toEntity(competition);
        competitionRepository.save(competitionEntity);
    }
}
