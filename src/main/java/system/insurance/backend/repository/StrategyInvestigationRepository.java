package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.StrategyInvestigation;

public interface StrategyInvestigationRepository extends JpaRepository<StrategyInvestigation, Integer> {
}
