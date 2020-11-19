package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.insurance.AuthorizationReport;

public interface AuthorizationReportRepository extends JpaRepository<AuthorizationReport, Integer> {
}
