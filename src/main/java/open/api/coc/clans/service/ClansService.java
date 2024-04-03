package open.api.coc.clans.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.external.coc.ClashOfClansApi;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClansService {

    private final ClashOfClansApi clashOfClansApi;


    public Map<String, Object> findClanByClanTag(String clanTag) {
        return clashOfClansApi.findClanByClanTag(clanTag);
    }
}
