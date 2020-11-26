package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dbo.insurance.SalesTarget;

import java.util.List;

public interface SalesTargetRepository extends JpaRepository<SalesTarget, Integer> {
    List<SalesTarget> findAllByInsurance(Insurance insurance);
}
