package open.api.coc.clans.domain.clans;

import lombok.Getter;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.external.coc.clan.domain.common.IconUrl;

@Getter
public class ClanCreateCommand {

    private final String tag;
    private final String name;
    private final IconUrl badgeUrl;

    private ClanCreateCommand(String tag, String name, IconUrl badgeUrl) {
        this.tag = tag;
        this.name = name;
        this.badgeUrl = badgeUrl;
    }

    public static ClanCreateCommand create(String clanTag, ClanCreateRequest request) {
        ClanCreateCommand command = new ClanCreateCommand(clanTag, request.name(), request.badgeUrl());
        command.validate();
        return command;
    }

    private void validate() {
        try {
            badgeUrl.validateNotEmptyFields();
        } catch (BadRequestException e) {
            e.addExtraMessage("badge_url fields can not be empty.");
            throw e;
        }
    }
}
