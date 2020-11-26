package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.accident.Accident;


public interface AccidentRepository extends JpaRepository<Accident, Integer> {
}
