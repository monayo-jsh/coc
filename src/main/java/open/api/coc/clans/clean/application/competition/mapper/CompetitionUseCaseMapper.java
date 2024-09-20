package open.api.coc.clans.clean.application.competition.mapper;

import open.api.coc.clans.clean.application.competition.model.CompetitionClanScheduleCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionClanScheduleDeleteCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateClanPlayerCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateClanPlayerDeleteCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionUpdateCommand;
import open.api.coc.clans.clean.domain.competition.model.Competition;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionClanScheduleCreateRequest;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionCreateRequest;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionDetailResponse;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionResponse;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = CompetitionClanUseCaseMapper.class
)
public interface CompetitionUseCaseMapper {

    // 대회 등록 커맨드
    CompetitionCreateCommand toCreateCommand(CompetitionCreateRequest request);

    // 대회 수정 커맨드
    CompetitionUpdateCommand toUpdateCommand(Long id, CompetitionUpdateRequest request);

    // 대회 응답
    CompetitionResponse toResponse(Competition competition);

    // 대회 상세 응답
    CompetitionDetailResponse toDetailResponse(Competition competition);


    // 대회 참가 신청 커맨드
    CompetitionParticipateCreateCommand toParticipateCreateCommand(Long competitionId, String clanTag);

    // 대회 참가 클랜 멤버 등록 커맨드
    CompetitionParticipateClanPlayerCreateCommand toParticipateClanPlayerCreateCommand(Long competitionId, String clanTag, String playerTag);

    // 대회 참가 클랜 멤버 삭제 커맨드
    CompetitionParticipateClanPlayerDeleteCommand toParticipateClanPlayerDeleteCommand(Long competitionId, String clanTag, String playerTag);

    // 대회 참가 클랜 라운드 일정 생성 커맨드
    @Mapping(target = "description", source = "request.name")
    @Mapping(target = "startDate", source = "request.startDate")
    @Mapping(target = "endDate", source = "request.endDate")
    CompetitionClanScheduleCreateCommand toCompetitionClanScheduleCreateCommand(Long competitionId, String clanTag, CompetitionClanScheduleCreateRequest request);

    // 대회 참가 클랜 라운드 일정 삭제 커맨드
    CompetitionClanScheduleDeleteCommand toCompetitionClanScheduleDeleteCommand(Long competitionId, String clanTag, Long clanScheduleId);
}
