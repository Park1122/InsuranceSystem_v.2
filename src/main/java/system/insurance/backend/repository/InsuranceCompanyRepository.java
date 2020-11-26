package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.InsuranceCompany;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Integer> {
    InsuranceCompany findByCompany(String hanhwa);
}
