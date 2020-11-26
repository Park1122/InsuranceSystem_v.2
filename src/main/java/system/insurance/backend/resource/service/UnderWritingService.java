package system.insurance.backend.resource.service;

import org.springframework.http.ResponseEntity;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.resource.dto.ClientDTO;
import system.insurance.backend.resource.dto.ContractDTO;
import system.insurance.backend.resource.dto.ContractDetailDTO;
import system.insurance.backend.resource.dto.UWPolicyDTO;

import java.util.List;

public interface UnderWritingService {

    List<UWPolicyDTO> getUnderWritingPolicyList();
    List<ContractDTO> getContractList(int eid) throws NoEmployeeException;
    boolean saveFactorsToClient();

    List<ContractDTO> getUnPassedContractList(int id) throws NoEmployeeException;

    ResponseEntity<ContractDetailDTO> getContractDetailFactors(int contractId);
}
