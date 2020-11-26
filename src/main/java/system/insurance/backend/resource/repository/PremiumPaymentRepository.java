package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.contract.Contract;
import system.insurance.backend.contract.PremiumPayment;

import java.util.List;

public interface PremiumPaymentRepository extends JpaRepository<PremiumPayment,Integer> {
    List<PremiumPayment> findAllByContract(Contract contract);
}
