package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.client.FinancialFactor;

public interface FinancialFactorRepository extends JpaRepository<FinancialFactor, Integer> {
}
