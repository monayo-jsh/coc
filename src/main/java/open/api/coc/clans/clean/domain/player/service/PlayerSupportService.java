package open.api.coc.clans.clean.domain.player.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.repository.PlayerSupportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerSupportService {

    private final PlayerSupportRepository supportRepository;

    @Transactional
    public void resetAll() {
        supportRepository.resetAll();
    }

    @Transactional
    public long updateBulk(List<String> playerTags) {
        return supportRepository.update(playerTags);
    }
}
