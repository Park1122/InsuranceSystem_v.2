package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dbo.insurance.InsuranceStatus;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    List<Insurance> findAllByStatus(InsuranceStatus status);
}
