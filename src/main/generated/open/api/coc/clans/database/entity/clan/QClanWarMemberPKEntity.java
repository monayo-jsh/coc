package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanWarMemberPKEntity is a Querydsl query type for ClanWarMemberPKEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QClanWarMemberPKEntity extends BeanPath<ClanWarMemberPKEntity> {

    private static final long serialVersionUID = 1358843626L;

    public static final QClanWarMemberPKEntity clanWarMemberPKEntity = new QClanWarMemberPKEntity("clanWarMemberPKEntity");

    public final StringPath tag = createString("tag");

    public final NumberPath<Long> warId = createNumber("warId", Long.class);

    public QClanWarMemberPKEntity(String variable) {
        super(ClanWarMemberPKEntity.class, forVariable(variable));
    }

    public QClanWarMemberPKEntity(Path<? extends ClanWarMemberPKEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanWarMemberPKEntity(PathMetadata metadata) {
        super(ClanWarMemberPKEntity.class, metadata);
    }

}

