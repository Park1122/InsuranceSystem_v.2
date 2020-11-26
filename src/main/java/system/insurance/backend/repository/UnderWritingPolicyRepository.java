package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dbo.underWriting.UWPolicy;

import java.util.Optional;

public interface UnderWritingPolicyRepository extends JpaRepository<UWPolicy, Integer> {

    public Optional<UWPolicy> findByInsurance(Insurance insurance);

}
