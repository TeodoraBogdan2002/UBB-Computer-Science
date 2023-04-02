package lab4.mpp.labb4.repo;

import lab4.mpp.labb4.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface ClientRepository extends JpaRepository<Client, Long> {
}
