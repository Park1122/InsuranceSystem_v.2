package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.insurance.GuaranteeInfo;
import system.insurance.backend.dbo.insurance.Insurance;

import java.util.List;

public interface GuaranteeInfoRepository extends JpaRepository<GuaranteeInfo, Integer> {
    List<GuaranteeInfo> findAllByInsurance(Insurance insurance);
}
