package open.api.coc.clans.database.repository.lab;

import open.api.coc.clans.database.entity.lab.LabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LabRepository extends JpaRepository<LabEntity, Long> {
}
