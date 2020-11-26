package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.counseling.ClientCounseling;

public interface ClientCounselingRepository extends JpaRepository<ClientCounseling, Integer> {

}
