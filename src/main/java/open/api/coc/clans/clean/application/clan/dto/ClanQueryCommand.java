package open.api.coc.clans.clean.application.clan.dto;

import open.api.coc.clans.clean.domain.clan.model.ClanContentType;

public record ClanQueryCommand(

    ClanContentType type

) {

    public static ClanQueryCommand of(String type) {
        if (type == null) {
            return new ClanQueryCommand(null);
        }

        ClanContentType clanContentType = ClanContentType.fromType(type);
        return new ClanQueryCommand(clanContentType);
    }

    public boolean isSearchType() {
        return type != null;
    }

}
