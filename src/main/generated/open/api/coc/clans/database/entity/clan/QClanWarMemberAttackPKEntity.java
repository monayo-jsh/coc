package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanWarMemberAttackPKEntity is a Querydsl query type for ClanWarMemberAttackPKEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QClanWarMemberAttackPKEntity extends BeanPath<ClanWarMemberAttackPKEntity> {

    private static final long serialVersionUID = -1164070830L;

    public static final QClanWarMemberAttackPKEntity clanWarMemberAttackPKEntity = new QClanWarMemberAttackPKEntity("clanWarMemberAttackPKEntity");

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final StringPath tag = createString("tag");

    public final NumberPath<Long> warId = createNumber("warId", Long.class);

    public QClanWarMemberAttackPKEntity(String variable) {
        super(ClanWarMemberAttackPKEntity.class, forVariable(variable));
    }

    public QClanWarMemberAttackPKEntity(Path<? extends ClanWarMemberAttackPKEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanWarMemberAttackPKEntity(PathMetadata metadata) {
        super(ClanWarMemberAttackPKEntity.class, metadata);
    }

}

