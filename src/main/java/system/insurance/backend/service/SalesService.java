package system.insurance.backend.service;

import org.springframework.http.ResponseEntity;
import system.insurance.backend.dto.CounselingDTO;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.InstructionDTO;
import system.insurance.backend.dto.LossRateDTO;

import java.util.List;

public interface SalesService{
    boolean instructionRegister(String title, String instruction, int id) throws NoEmployeeException;

    List<InstructionDTO> getSalesInstructionList();

    boolean saveCounselingRecord(String content, int eid) throws NoEmployeeException;


    List<LossRateDTO> getLossRateListFor(int term);

    List<CounselingDTO> getRecordsByEmployeeId(int eid) throws NoEmployeeException;

    CounselingDTO getRecordByCounselingId(int id);
}
