package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.dbo.contract.PremiumPayment;

import java.util.List;

public interface PremiumPaymentRepository extends JpaRepository<PremiumPayment,Integer> {
    List<PremiumPayment> findAllByContract(Contract contract);
}
