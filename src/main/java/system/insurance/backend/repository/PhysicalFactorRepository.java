package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.client.PhysicalFactor;

public interface PhysicalFactorRepository extends JpaRepository<PhysicalFactor, Integer> {
}
