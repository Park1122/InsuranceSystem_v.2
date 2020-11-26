package system.insurance.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.insurance.backend.dbo.accident.Accident;
import system.insurance.backend.dbo.accident.AccidentType;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.exception.NoAccidentException;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.dto.ContractDTO;
import system.insurance.backend.repository.AccidentRepository;
import system.insurance.backend.repository.ClientRepository;
import system.insurance.backend.repository.ContractRepository;

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

    @Override
    public boolean saveHandledAccident(int accidentId, String scenario, String damage, String picture, String video,
                                       String record, String processingCost) throws NoAccidentException{
        Optional<Accident> optAccident = this.accidentRepository.findById(accidentId);
        Accident accident = optAccident.orElseThrow(NoAccidentException::new);
        accident.getInquiryInfo().setScenario(scenario);
        accident.getInquiryInfo().setPicture(picture);
        accident.getInquiryInfo().setVideo(video);
        accident.getInquiryInfo().setRecord(record);
        accident.getInquiryInfo().setProcessingCost(Long.parseLong(processingCost));
        this.accidentRepository.save(accident);
        return true;
    }

}
