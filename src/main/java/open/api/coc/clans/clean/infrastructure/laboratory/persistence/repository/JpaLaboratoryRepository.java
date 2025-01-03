package open.api.coc.clans.clean.infrastructure.laboratory.persistence.repository;

import open.api.coc.clans.clean.infrastructure.laboratory.persistence.entity.LaboratoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaLaboratoryRepository extends JpaRepository<LaboratoryEntity, Long> {
}
