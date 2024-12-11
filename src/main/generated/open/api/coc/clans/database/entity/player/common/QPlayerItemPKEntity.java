package open.api.coc.clans.database.entity.player.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlayerItemPKEntity is a Querydsl query type for PlayerItemPKEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPlayerItemPKEntity extends BeanPath<PlayerItemPKEntity> {

    private static final long serialVersionUID = -12179840L;

    public static final QPlayerItemPKEntity playerItemPKEntity = new QPlayerItemPKEntity("playerItemPKEntity");

    public final StringPath name = createString("name");

    public final StringPath playerTag = createString("playerTag");

    public QPlayerItemPKEntity(String variable) {
        super(PlayerItemPKEntity.class, forVariable(variable));
    }

    public QPlayerItemPKEntity(Path<? extends PlayerItemPKEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlayerItemPKEntity(PathMetadata metadata) {
        super(PlayerItemPKEntity.class, metadata);
    }

}

