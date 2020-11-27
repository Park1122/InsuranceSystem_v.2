package system.insurance.backend.service;

import system.insurance.backend.dbo.accident.AccidentType;
import system.insurance.backend.exception.NoAccidentException;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.dto.ContractDTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AccidentService {
    List<ContractDTO> checkRegisteredClient(String name, String rrn) throws NoClientException;

    boolean addAccident(int contractId, String accidentArea, AccidentType accidentType, LocalDateTime date);

    boolean saveHandledAccident(int accidentId, String scenario, String damage, String picture,
                                String video, String record, String processingCost) throws NoAccidentException;
}
