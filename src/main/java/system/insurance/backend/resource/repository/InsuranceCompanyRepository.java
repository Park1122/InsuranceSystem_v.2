package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.insurance.InsuranceCompany;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Integer> {
    InsuranceCompany findByCompany(String hanhwa);
}
