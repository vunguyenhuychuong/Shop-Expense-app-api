package in.bushansirgur.expensetrackerapi.repository;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import in.bushansirgur.expensetrackerapi.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
