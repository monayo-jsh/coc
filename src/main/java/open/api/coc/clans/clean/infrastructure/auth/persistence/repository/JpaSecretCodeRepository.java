package open.api.coc.clans.clean.infrastructure.auth.persistence.repository;

import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.auth.persistence.entity.SecretCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSecretCodeRepository extends JpaRepository<SecretCodeEntity, Long> {

    Optional<SecretCodeEntity> findByCode(String code);

}
