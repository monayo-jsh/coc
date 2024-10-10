package open.api.coc.clans.clean.infrastructure.season.repository;

import static open.api.coc.clans.clean.infrastructure.season.entity.QSeasonEndManagementEntity.seasonEndManagementEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaSeasonEndManagementCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<LocalDate> findSeasonEndDateBy(LocalDate baseDate) {
        LocalDate startOfMonth = baseDate.withDayOfMonth(1);
        LocalDate endOfMonth = baseDate.withDayOfMonth(baseDate.lengthOfMonth());

        LocalDate seasonEndDate = queryFactory.select(seasonEndManagementEntity.endDate.max())
                                              .from(seasonEndManagementEntity)
                                              .where(seasonEndManagementEntity.endDate.between(startOfMonth, endOfMonth))
                                              .fetchOne();

        return Optional.ofNullable(seasonEndDate);
    }

    public List<LocalDate> findLatestSeasonEndDate(int count) {
        return queryFactory.select(seasonEndManagementEntity.endDate)
                           .from(seasonEndManagementEntity)
                           .orderBy(seasonEndManagementEntity.endDate.desc())
                           .limit(count)
                           .fetch();
    }
}
