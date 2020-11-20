package system.insurance.backend.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.insurance.backend.accident.Accident;
import system.insurance.backend.accident.AccidentType;
import system.insurance.backend.client.Client;
import system.insurance.backend.contract.Contract;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.resource.dto.ContractDTO;
import system.insurance.backend.resource.repository.AccidentRepository;
import system.insurance.backend.resource.repository.ClientRepository;
import system.insurance.backend.resource.repository.ContractRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccidentServiceImpl implements AccidentService{
    private final ClientRepository clientRepository;
    private final AccidentRepository accidentRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public AccidentServiceImpl(ClientRepository clientRepository, AccidentRepository accidentRepository, ContractRepository contractRepository) {
        this.clientRepository = clientRepository;
        this.accidentRepository = accidentRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public List<ContractDTO> checkRegisteredClient(String name, String rrn) throws NoClientException {
        Optional<Client> clientOptional = this.clientRepository.findByRrn(rrn);
        Client client = clientOptional.orElseThrow(NoClientException::new);
        List<Contract> contractList = this.contractRepository.findAllByClient(client);
        List<ContractDTO> contractDTOList = new ArrayList<>();
        contractList.forEach(contract -> contractDTOList.add(ContractDTO.builder()
                .id(contract.getId())
                .insuranceType(contract.getInsurance().getType())
                .build()));
        return contractDTOList;
    }

    @Override
    public boolean addAccident(int contractId, String accidentArea, AccidentType accidentType, Date date){
        this.accidentRepository.save(Accident.builder()
                .accidentArea(accidentArea)
                .accidentType(accidentType)
                .date(date)
                .contractId(contractId)
                .build());
        return true;
    }
}
