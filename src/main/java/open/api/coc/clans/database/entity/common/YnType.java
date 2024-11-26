package open.api.coc.clans.database.entity.common;

import lombok.Getter;

@Getter
public enum YnType {

    Y,N;

    public static YnType from(String type) {
        if (type == null) return null;
        return valueOf(type.toUpperCase());
    }
}
