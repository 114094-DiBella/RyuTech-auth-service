package ryutech.authservice.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ryutech.authservice.entities.UserEntity;
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> , JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByEmail(String email);
}
