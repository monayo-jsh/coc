package open.api.coc.clans.clean.domain.season.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.season.exception.SeasonNotExistsException;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeasonDeletionService {

    private final SeasonRepository seasonRepository;

    public void delete(Long seasonEndDate) {
        // 종료일 변환
        LocalDate endDate = TimeUtils.parseLocalDate(seasonEndDate);

        // 종료일 조회
        LocalDate existingEndDate = seasonRepository.findSeasonEndDateByBaseDate(endDate)
                                                    .orElseThrow(() -> new SeasonNotExistsException(endDate));

        // 종료일 삭제
        seasonRepository.remove(existingEndDate);
    }
}
