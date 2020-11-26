package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.client.EnvironmentalFactor;

public interface EnvironmentalFactorRepository extends JpaRepository<EnvironmentalFactor, Integer> {

}
