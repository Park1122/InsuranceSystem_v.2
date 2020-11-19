package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.insurance.AuthorizationReport;
import system.insurance.backend.insurance.EvaluationReport;
import system.insurance.backend.insurance.Insurance;

import java.util.List;

public interface AuthorizationReportRepository extends JpaRepository<AuthorizationReport, Integer> {
}
