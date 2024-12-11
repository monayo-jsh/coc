package open.api.coc.clans.database.entity.player.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlayerItemEntity is a Querydsl query type for PlayerItemEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPlayerItemEntity extends BeanPath<PlayerItemEntity> {

    private static final long serialVersionUID = -1148252859L;

    public static final QPlayerItemEntity playerItemEntity = new QPlayerItemEntity("playerItemEntity");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> maxLevel = createNumber("maxLevel", Integer.class);

    public QPlayerItemEntity(String variable) {
        super(PlayerItemEntity.class, forVariable(variable));
    }

    public QPlayerItemEntity(Path<? extends PlayerItemEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlayerItemEntity(PathMetadata metadata) {
        super(PlayerItemEntity.class, metadata);
    }

}

