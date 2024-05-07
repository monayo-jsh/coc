package open.api.coc.clans.domain.clans;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.common.AcademeClan;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueClanRes {
    private String tag;
    private String name;
    private List<String> warTags;


    public static LeagueClanRes create(AcademeClan clan) throws IOException {
        return LeagueClanRes.builder()
                .name(clan.getName())
                .tag(clan.getTag())
                .warTags(getWarTags(clan.getTag()))
                .build();
    }

    public static List<String> getWarTags(String tag) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("./src/main/resources/static/clanwarleague/" + tag + File.separator + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")) + "_war_rounds.json");

        // JSON 파일 읽기
        ObjectMapper mapper = new ObjectMapper();
        WarTagsData warTagsData = mapper.readValue(file, WarTagsData.class);

        // 필요한 정보 추출
        List<String> warTags = warTagsData.getWarTags();

        return warTags;
    }

    // JSON 데이터를 매핑할 클래스
    private static class WarTagsData {
        private List<String> warTags;

        public List<String> getWarTags() {
            return warTags;
        }

        public void setWarTags(List<String> warTags) {
            this.warTags = warTags;
        }
    }
}
