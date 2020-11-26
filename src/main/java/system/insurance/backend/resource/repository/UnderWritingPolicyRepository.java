package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.insurance.Insurance;
import system.insurance.backend.underWriting.UWPolicy;

import java.util.Optional;

public interface UnderWritingPolicyRepository extends JpaRepository<UWPolicy, Integer> {

    public Optional<UWPolicy> findByInsurance(Insurance insurance);

}
