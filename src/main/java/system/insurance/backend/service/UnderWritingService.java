package system.insurance.backend.service;

import org.springframework.http.ResponseEntity;
import system.insurance.backend.dbo.client.Job;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dto.*;
import system.insurance.backend.exception.NoEmployeeException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UnderWritingService {

    List<UWPolicyDTO> getUnderWritingPolicyList();

    void saveContractStatus(int contractId, String status);

    List<ContractDTO> getContractList(int eid) throws NoEmployeeException;
    boolean saveFactorsToClient();

    List<ContractDTO> getUnPassedContractList(int id) throws NoEmployeeException;

    ResponseEntity<ContractDetailDTO> getContractDetailFactors(int contractId);
    Optional<UWPolicyDTO> getUnderWritingPolicy(int pid);

    Map<Integer, ClientFactorDTO> getOnProgressContractAndLessFactorCustomers(int id);

    void savePremiumRate(int cid);

    Long calculatePremiumRate(Insurance insurance, Job clientJob);

    Map<Integer, ContractDetailDTO> findAllOnProgressContractList(int cid);
}
