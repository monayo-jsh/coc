package open.api.coc.clans.clean.application.clan.dto;

import open.api.coc.clans.clean.application.common.dto.IconUrlCommand;

public record ClanCreateCommand(

    String tag,
    String name,
    IconUrlCommand badgeUrl

) {

    public ClanCreateCommand(String tag, String name, IconUrlCommand badgeUrl) {
        this.tag = tag;
        this.name = name;
        this.badgeUrl = badgeUrl;

        validate();
    }

    private void validate() {
        badgeUrl.validateAllFieldsEmpty();
    }
}
