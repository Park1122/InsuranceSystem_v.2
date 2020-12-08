package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.counseling.ClientCounseling;
import system.insurance.backend.dbo.employee.Employee;

import java.util.List;

public interface ClientCounselingRepository extends JpaRepository<ClientCounseling, Integer> {

    List<ClientCounseling> findAllByCounselor(Employee employee1);
}
