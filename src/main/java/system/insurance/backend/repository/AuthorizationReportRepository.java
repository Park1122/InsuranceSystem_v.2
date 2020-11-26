package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.AuthorizationReport;

public interface AuthorizationReportRepository extends JpaRepository<AuthorizationReport, Integer> {
}
