package open.api.coc.clans.clean.domain.season.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.season.exception.SeasonDuplicateException;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeasonRegisterService {

    private final SeasonRepository seasonRepository;

    public void create(Long seasonEndDate) {
        // 종료일 변환
        LocalDate endDate = TimeUtils.parseLocalDate(seasonEndDate);

        // 시즌 종료일이 이미 설정되었는지 확인
        ensureSeasonEndDateDoesNotExists(endDate);

        // 시즌 종료일 저장
        seasonRepository.save(endDate);
    }

    private void ensureSeasonEndDateDoesNotExists(LocalDate endDate) {
        seasonRepository.findSeasonEndDateByBaseDate(endDate)
                        .ifPresent(existingLastEndDate -> {
                            if (existingLastEndDate.isEqual(endDate)) {
                                throw new SeasonDuplicateException(endDate);
                            }
                        });
    }

}
