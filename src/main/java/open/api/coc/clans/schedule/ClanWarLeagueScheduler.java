package open.api.coc.clans.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.common.AcademeClan;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.clan.WarClan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClanWarLeagueScheduler {
    private final ClanApiService clanApiService;
    private final ObjectMapper objectMapper;
    private static final String RESOURCE_ROOT =  "./src/main/resources/static/clanwarleague";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

    @Scheduled(cron = "0 30 23 1-8 * *")
    public void createWarRoundFile() throws IOException {
        createFile();
    }

    private void createFile() throws JsonProcessingException {
        List<String> clanTags = AcademeClan.getClanWarLeagueTagList();
        for(String clanTag : clanTags) {
            List<LinkedHashMap<String,List<String>>> rounds = clanApiService.findClanWarLeagueRoundTags(clanTag);
            List<String> newTags = new ArrayList<>();
            for(LinkedHashMap<String, List<String>> round : rounds) {
                for(String roundTag : round.get("warTags")) {
                    boolean founded = false;
                    if(!roundTag.equals("#0") && isAcademyRound(clanTag, roundTag)) {
                        founded = true;
                        newTags.add(roundTag);
                    }
                    if(founded) {
                        break;
                    }
                }
            }
            Map<String, List<String>> jsonResult = new HashMap<>();
            jsonResult.put("warTags", newTags);
            try {
                ClassPathResource resource = new ClassPathResource(RESOURCE_ROOT +File.separator + clanTag);
                if (!resource.exists()) {
                    File directory = new File(resource.getPath());
                    directory.mkdirs();
                }

                File file = new File(resource.getPath(),LocalDate.now().format(formatter) + "_war_rounds.json");
                if (!file.exists()) {
                    boolean result = file.createNewFile();
                    if (!result) {
                        log.error("파일 생성 실패: " + file.getAbsolutePath());
                        continue;
                    }
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.append(objectMapper.writeValueAsString(jsonResult));
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                log.error("파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage(), e);
            }
        }
    }

    private boolean isAcademyRound(String clanTag, String roundTag) {
        Optional<ClanWar> leagueWar = clanApiService.findLeagueWarByRoundTag(roundTag);
        if (leagueWar.isPresent()) {
            WarClan clan = leagueWar.get().getClan();
            WarClan opponent = leagueWar.get().getOpponent();
            return clan.getTag().equals(clanTag) || opponent.getTag().equals(clanTag);
        }
        return false;
    }

}
