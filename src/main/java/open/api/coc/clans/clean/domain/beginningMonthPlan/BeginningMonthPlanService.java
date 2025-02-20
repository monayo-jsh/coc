package open.api.coc.clans.clean.domain.beginningMonthPlan;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.beginningMonthPlan.dto.BeginningMonthCreateCommand;
import open.api.coc.clans.clean.domain.beginningMonthPlan.exception.BeginningMonthAlreadyExistsException;
import open.api.coc.clans.clean.domain.beginningMonthPlan.mapper.BeginningMonthMapper;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;
import open.api.coc.clans.clean.domain.beginningMonthPlan.repository.BeginningMonthRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeginningMonthPlanService {

    private final BeginningMonthMapper beginningMonthMapper;
    private final BeginningMonthRepository beginningMonthRepository;

    @Transactional
    public void create(BeginningMonthCreateCommand command) {
        // 월초 일정 등록된 데이터 검증
        beginningMonthRepository.findByMonth(command.month())
                                .ifPresent((beginningMonth) -> {throw new BeginningMonthAlreadyExistsException(); });

        // 월초 일정 도메인 생성
        BeginningMonth beginningMonth = beginningMonthMapper.toBeginningMonth(command);

        // 월초 일정 저장
        beginningMonthRepository.save(beginningMonth);

    }

}
