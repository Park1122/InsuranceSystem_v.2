package system.insurance.backend.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.insurance.backend.accident.Accident;
import system.insurance.backend.accident.AccidentType;
import system.insurance.backend.client.Client;
import system.insurance.backend.exception.NoAccidentException;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.resource.dto.AccidentDTO;
import system.insurance.backend.resource.repository.AccidentRepository;
import system.insurance.backend.resource.repository.ClientRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class AccidentServiceImpl implements AccidentService{
    private final ClientRepository clientRepository;
    private final AccidentRepository accidentRepository;

    @Autowired
    public AccidentServiceImpl(ClientRepository clientRepository, AccidentRepository accidentRepository) {
        this.clientRepository = clientRepository;
        this.accidentRepository = accidentRepository;
    }

    @Override
    public boolean checkRegisteredClient(String name, String rrn) throws NoClientException {
        Optional<Client> clientOptional = this.clientRepository.findByRrn(rrn);
        Client client = clientOptional.orElseThrow(NoClientException::new);
        return client != null;
    }

    @Override
    public boolean addAccident(String accidentArea, AccidentType accidentType, Date date) throws NoAccidentException {
        this.accidentRepository.save(Accident.builder()
                .accidentArea(accidentArea)
                .accidentType(accidentType)
                .date(date)
                .build());
        return true;
    }
}
