package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.insurance.backend.accident.Accident;


public interface AccidentRepository extends JpaRepository<Accident, Integer> {
}
