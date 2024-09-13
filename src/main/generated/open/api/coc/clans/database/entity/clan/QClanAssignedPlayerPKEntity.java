package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanAssignedPlayerPKEntity is a Querydsl query type for ClanAssignedPlayerPKEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QClanAssignedPlayerPKEntity extends BeanPath<ClanAssignedPlayerPKEntity> {

    private static final long serialVersionUID = 1952853955L;

    public static final QClanAssignedPlayerPKEntity clanAssignedPlayerPKEntity = new QClanAssignedPlayerPKEntity("clanAssignedPlayerPKEntity");

    public final StringPath playerTag = createString("playerTag");

    public final StringPath seasonDate = createString("seasonDate");

    public QClanAssignedPlayerPKEntity(String variable) {
        super(ClanAssignedPlayerPKEntity.class, forVariable(variable));
    }

    public QClanAssignedPlayerPKEntity(Path<? extends ClanAssignedPlayerPKEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanAssignedPlayerPKEntity(PathMetadata metadata) {
        super(ClanAssignedPlayerPKEntity.class, metadata);
    }

}

