package open.api.coc.clans.clean.domain.competition.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionAlreadyExistsException;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionNotExistsException;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionMapper;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    public List<Competition> findAll() {
        List<CompetitionEntity> competitionEntities = competitionRepository.findAll();
        return competitionEntities.stream()
                                  .map(competitionMapper::toDomain)
                                  .toList();
    }

    public void ensureCompetitionDoesNotExist(String name, LocalDate startDate, LocalDate endDate) {
        if (competitionRepository.exists(name, startDate, endDate)) {
            throw new CompetitionAlreadyExistsException("%s - %s ~ %s".formatted(name,
                                                                                 TimeUtils.formattedISODate(startDate),
                                                                                 TimeUtils.formattedISODate(endDate)));
        }
    }

    @Transactional
    public Competition create(Competition competition) {
        ensureCompetitionDoesNotExist(competition.getName(), competition.getStartDate(), competition.getEndDate());

        CompetitionEntity competitionEntity = competitionMapper.toEntity(competition);
        CompetitionEntity saveCompetitionEntity = competitionRepository.save(competitionEntity);
        return competitionMapper.toDomain(saveCompetitionEntity);
    }

    @Transactional(readOnly = true)
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
