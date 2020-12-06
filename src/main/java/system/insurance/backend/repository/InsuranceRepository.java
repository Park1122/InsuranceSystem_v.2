package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dbo.insurance.InsuranceStatus;
import system.insurance.backend.dbo.underWriting.UWPolicy;

import java.util.List;
import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    List<Insurance> findAllByStatus(InsuranceStatus status);


    Optional<Insurance> findByUwPolicy(UWPolicy uwPolicy);
}
