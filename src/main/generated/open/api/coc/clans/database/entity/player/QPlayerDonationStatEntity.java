package open.api.coc.clans.database.entity.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlayerDonationStatEntity is a Querydsl query type for PlayerDonationStatEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerDonationStatEntity extends EntityPathBase<PlayerDonationStatEntity> {

    private static final long serialVersionUID = -2050456097L;

    public static final QPlayerDonationStatEntity playerDonationStatEntity = new QPlayerDonationStatEntity("playerDonationStatEntity");

    public final NumberPath<Integer> donationsDelta = createNumber("donationsDelta", Integer.class);

    public final NumberPath<Integer> donationsReceivedDelta = createNumber("donationsReceivedDelta", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final StringPath playerTag = createString("playerTag");

    public final StringPath season = createString("season");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QPlayerDonationStatEntity(String variable) {
        super(PlayerDonationStatEntity.class, forVariable(variable));
    }

    public QPlayerDonationStatEntity(Path<? extends PlayerDonationStatEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlayerDonationStatEntity(PathMetadata metadata) {
        super(PlayerDonationStatEntity.class, metadata);
    }

}

