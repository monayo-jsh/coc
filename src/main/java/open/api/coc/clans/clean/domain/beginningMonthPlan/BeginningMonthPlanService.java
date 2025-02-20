package open.api.coc.clans.clean.domain.beginningMonthPlan;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.beginningMonthPlan.dto.BeginningMonthCreateCommand;
import open.api.coc.clans.clean.domain.beginningMonthPlan.exception.BeginningMonthAlreadyExistsException;
import open.api.coc.clans.clean.domain.beginningMonthPlan.mapper.BeginningMonthMapper;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;
import open.api.coc.clans.clean.domain.beginningMonthPlan.repository.BeginningMonthRepository;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeginningMonthPlanService {

    private final BeginningMonthMapper beginningMonthMapper;
    private final BeginningMonthRepository beginningMonthRepository;

    public List<BeginningMonthResponse> gets() {
        // 월초 일정 목록 조회
        int limit = 10;
        List<BeginningMonth> beginningMonths = beginningMonthRepository.findAll(limit);

        // 응답
        return beginningMonths.stream()
                              .map(beginningMonthMapper::toBeginningMonthResponse)
                              .toList();
    }

    public BeginningMonthResponse getLatest() {
        // 최근 월초 일정 조회
        BeginningMonth beginningMonth = beginningMonthRepository.findLatest();

        // 월초 일정 응답
        return beginningMonthMapper.toBeginningMonthResponse(beginningMonth);
    }

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

    @Transactional
    public void delete(Long id) {
        beginningMonthRepository.findById(id)
                                .ifPresent(beginningMonthRepository::delete);
    }
}
