package open.api.coc.clans.database.entity.lab;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLabEntity is a Querydsl query type for LabEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabEntity extends EntityPathBase<LabEntity> {

    private static final long serialVersionUID = -1029748585L;

    public static final QLabEntity labEntity = new QLabEntity("labEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final StringPath name = createString("name");

    public QLabEntity(String variable) {
        super(LabEntity.class, forVariable(variable));
    }

    public QLabEntity(Path<? extends LabEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabEntity(PathMetadata metadata) {
        super(LabEntity.class, metadata);
    }

}

