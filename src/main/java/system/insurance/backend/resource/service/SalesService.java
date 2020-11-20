package system.insurance.backend.resource.service;

import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.resource.dto.ContractDTO;
import system.insurance.backend.resource.dto.InstructionDTO;
import system.insurance.backend.resource.dto.LossRateDTO;

import java.util.List;

public interface SalesService{
    boolean instructionRegister(String title, String instruction, int id) throws NoEmployeeException;

    List<InstructionDTO> getSalesInstructionList();
    List<ContractDTO> getContractList(int eid) throws NoEmployeeException;
    boolean saveCounselingRecord(String content, int eid) throws NoEmployeeException;


    List<LossRateDTO> getLossRateListFor(int term);
}
