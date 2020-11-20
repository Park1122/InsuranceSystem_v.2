package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.client.PhysicalFactor;

public interface PhysicalFactorRepository extends JpaRepository<PhysicalFactor, Integer> {
}
