package lab4.mpp.labb4.repo;

import lab4.mpp.labb4.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public
interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByNrkilometersGreaterThanEqual(int minNrKilometers);
}
