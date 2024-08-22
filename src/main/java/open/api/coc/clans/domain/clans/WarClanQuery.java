package open.api.coc.clans.domain.clans;

import lombok.Getter;

@Getter
public class WarClanQuery {

    private final String type;

    private WarClanQuery(String type) {
        this.type = type;
    }

    public static WarClanQuery create(String type) {
        // type이 null 이거나 빈 문자열인 경우 기본값 설정
        if (type == null || type.isBlank()) {
            type = "normal";
        }

        return new WarClanQuery(type);
    }

    public boolean isClanWar() {
        return "normal".equalsIgnoreCase(type);
    }

    public boolean isParallelClanWar() {
        return "parallel".equalsIgnoreCase(type);
    }

    public boolean isLeaugeWar() {
        return "league".equalsIgnoreCase(type);
    }
}
