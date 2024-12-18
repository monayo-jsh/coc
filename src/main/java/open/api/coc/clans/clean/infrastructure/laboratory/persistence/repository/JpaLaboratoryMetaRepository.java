package open.api.coc.clans.clean.infrastructure.laboratory.persistence.repository;

import open.api.coc.clans.clean.infrastructure.laboratory.persistence.entity.LaboratoryMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaLaboratoryMetaRepository extends JpaRepository<LaboratoryMetaEntity, Long> {

    @Query(value = """
      select meta from LaboratoryMetaEntity meta order by meta.createdAt desc limit 1
    """)
    LaboratoryMetaEntity findLatestOne();
}
