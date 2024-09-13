package open.api.coc.clans.database.entity.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIconUrlEntity is a Querydsl query type for IconUrlEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QIconUrlEntity extends BeanPath<IconUrlEntity> {

    private static final long serialVersionUID = -1480525362L;

    public static final QIconUrlEntity iconUrlEntity = new QIconUrlEntity("iconUrlEntity");

    public final StringPath large = createString("large");

    public final StringPath medium = createString("medium");

    public final StringPath small = createString("small");

    public final StringPath tiny = createString("tiny");

    public QIconUrlEntity(String variable) {
        super(IconUrlEntity.class, forVariable(variable));
    }

    public QIconUrlEntity(Path<? extends IconUrlEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIconUrlEntity(PathMetadata metadata) {
        super(IconUrlEntity.class, metadata);
    }

}

