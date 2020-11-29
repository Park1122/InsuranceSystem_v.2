package system.insurance.backend.service;

import org.springframework.http.ResponseEntity;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.ContractDTO;
import system.insurance.backend.dto.ContractDetailDTO;
import system.insurance.backend.dto.UWPolicyDTO;

import java.util.List;
import java.util.Optional;

public interface UnderWritingService {

    List<UWPolicyDTO> getUnderWritingPolicyList();
    List<ContractDTO> getContractList(int eid) throws NoEmployeeException;
    boolean saveFactorsToClient();

    List<ContractDTO> getUnPassedContractList(int id) throws NoEmployeeException;

    ResponseEntity<ContractDetailDTO> getContractDetailFactors(int contractId);
    public Optional<UWPolicyDTO> getUnderWritingPolicy(int pid);
}
