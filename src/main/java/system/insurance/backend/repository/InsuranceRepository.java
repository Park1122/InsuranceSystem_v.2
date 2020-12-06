package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dbo.insurance.InsuranceStatus;
import system.insurance.backend.dbo.underWriting.UWPolicy;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    List<Insurance> findAllByStatus(InsuranceStatus status);

    List<Insurance> findAllByUwPolicy(UWPolicy uwPolicy);

    List<Insurance> findAllByUwPolicyAndStatus(UWPolicy uwPolicy, InsuranceStatus onSale);
}
