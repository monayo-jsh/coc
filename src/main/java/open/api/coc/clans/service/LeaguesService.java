package open.api.coc.clans.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.clans.domain.clans.converter.LabelResponseConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaguesService {

    private final ClanApiService clanApiService;

    private final LabelResponseConverter labelResponseConverter;

    public List<LabelResponse> getLeagues() {
        LabelList labelList = clanApiService.findLeagues()
                                            .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "리그 목록 조회 실패"));

        return labelList.getItems()
                        .stream()
                        .map(labelResponseConverter::convert)
                        .collect(Collectors.toList());
    }
}
