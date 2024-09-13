package open.api.coc.clans.database.entity.raid;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRaidEntity is a Querydsl query type for RaidEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRaidEntity extends EntityPathBase<RaidEntity> {

    private static final long serialVersionUID = -1149138151L;

    public static final QRaidEntity raidEntity = new QRaidEntity("raidEntity");

    public final StringPath clanTag = createString("clanTag");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<RaiderEntity, QRaiderEntity> raiderEntityList = this.<RaiderEntity, QRaiderEntity>createList("raiderEntityList", RaiderEntity.class, QRaiderEntity.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QRaidEntity(String variable) {
        super(RaidEntity.class, forVariable(variable));
    }

    public QRaidEntity(Path<? extends RaidEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRaidEntity(PathMetadata metadata) {
        super(RaidEntity.class, metadata);
    }

}

