package open.api.coc.clans.clean.infrastructure.season.repository;

import java.time.LocalDate;
import open.api.coc.clans.clean.infrastructure.season.entity.SeasonEndManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSeasonEndManagementRepository extends JpaRepository<SeasonEndManagementEntity, LocalDate> {
}
