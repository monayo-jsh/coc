package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanWarWhitelistEntity is a Querydsl query type for ClanWarWhitelistEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanWarWhitelistEntity extends EntityPathBase<ClanWarWhitelistEntity> {

    private static final long serialVersionUID = 864830648L;

    public static final QClanWarWhitelistEntity clanWarWhitelistEntity = new QClanWarWhitelistEntity("clanWarWhitelistEntity");

    public final StringPath playerTag = createString("playerTag");

    public QClanWarWhitelistEntity(String variable) {
        super(ClanWarWhitelistEntity.class, forVariable(variable));
    }

    public QClanWarWhitelistEntity(Path<? extends ClanWarWhitelistEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanWarWhitelistEntity(PathMetadata metadata) {
        super(ClanWarWhitelistEntity.class, metadata);
    }

}

