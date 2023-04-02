package lab4.mpp.labb4.repo;

import lab4.mpp.labb4.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
