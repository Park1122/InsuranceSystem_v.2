package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.MarketInvestigation;

public interface MarketInvestigationRepository extends JpaRepository<MarketInvestigation, Integer> {
}
