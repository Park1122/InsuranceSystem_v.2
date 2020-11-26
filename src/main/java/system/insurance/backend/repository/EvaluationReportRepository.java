package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.EvaluationReport;
import system.insurance.backend.dbo.insurance.Insurance;

import java.util.List;

public interface EvaluationReportRepository extends JpaRepository<EvaluationReport, Integer> {
    List<EvaluationReport> findAllByInsurance(Insurance insurance);
}
