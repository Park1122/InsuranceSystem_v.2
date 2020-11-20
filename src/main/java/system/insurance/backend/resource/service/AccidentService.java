package system.insurance.backend.resource.service;

import system.insurance.backend.accident.AccidentType;
import system.insurance.backend.exception.NoAccidentException;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.resource.dto.AccidentDTO;

import java.util.Date;

public interface AccidentService {
    boolean checkRegisteredClient(String name, String rrn) throws NoClientException;

    boolean addAccident(String accidentArea, AccidentType accidentType, Date date) throws NoAccidentException;
}
