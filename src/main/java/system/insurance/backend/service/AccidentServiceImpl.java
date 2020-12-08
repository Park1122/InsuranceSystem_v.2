package system.insurance.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.insurance.backend.dbo.accident.Accident;
import system.insurance.backend.dbo.accident.AccidentType;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.dbo.insurance.EvaluationReport;
import system.insurance.backend.dbo.insurance.GuaranteeInfo;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dbo.insurance.SalesTarget;
import system.insurance.backend.dto.ClientDTO;
import system.insurance.backend.dto.GuaranteeInfoWrapper;
import system.insurance.backend.dto.InsuranceDTO;
import system.insurance.backend.exception.NoAccidentException;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.repository.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccidentServiceImpl implements AccidentService{
    private final ClientRepository clientRepository;
    private final AccidentRepository accidentRepository;
    private final ContractRepository contractRepository;
    private final GuaranteeInfoRepository guaranteeRepository;
    private final SalesTargetRepository salesTargetRepository;
    private final EvaluationReportRepository evaluationReportRepository;

    @Autowired
    public AccidentServiceImpl(ClientRepository clientRepository, AccidentRepository accidentRepository, ContractRepository contractRepository,
                               GuaranteeInfoRepository guaranteeRepository, SalesTargetRepository salesTargetRepository,
                               EvaluationReportRepository evaluationReportRepository) {
        this.clientRepository = clientRepository;
        this.accidentRepository = accidentRepository;
        this.contractRepository = contractRepository;
        this.guaranteeRepository=guaranteeRepository;
        this.salesTargetRepository=salesTargetRepository;
        this.evaluationReportRepository=evaluationReportRepository;
    }

    @Override
    public ClientDTO checkRegisteredClient(String name, String contact) throws NoClientException {
        Optional<Client> clientOptional = this.clientRepository.findByContact(contact);
        Client client = clientOptional.orElseThrow(NoClientException::new);
        List<Contract> contractList = this.contractRepository.findAllByClient(client);

        Map<String, InsuranceDTO> insuranceDTOMap = new HashMap<>();
        contractList.forEach(contract -> {
            Insurance insurance= contract.getInsurance();

            List<GuaranteeInfo> guaranteeInfoList = this.guaranteeRepository.findAllByInsurance(insurance);
            List<SalesTarget> salesTargetList = this.salesTargetRepository.findAllByInsurance(insurance);
            List<EvaluationReport> evaluationReportList = this.evaluationReportRepository.findAllByInsurance(insurance);

            Map<Integer, String> salesTargetStringList = new HashMap<>();
            Map<Integer, GuaranteeInfoWrapper> guaranteeInfoStringList = new HashMap<>();
            Map<Integer, String> evaluationInfo = new HashMap<>();

            guaranteeInfoList.forEach(guaranteeInfo -> guaranteeInfoStringList.put(guaranteeInfo.getId(), GuaranteeInfoWrapper.builder()
                    .condition(guaranteeInfo.getGuaranteeCondition())
                    .limit(guaranteeInfo.getGuaranteeLimit())
                    .special(guaranteeInfo.isSpecialCondition())
                    .build()));
            salesTargetList.forEach(salesTarget -> salesTargetStringList.put(salesTarget.getId(), salesTarget.getTarget()));
            evaluationReportList.forEach(evaluationReport -> evaluationInfo.put(evaluationReport.getId(), evaluationReport.getDate() + " " +
                    new File(evaluationReport.getPath()).getName()));

            insuranceDTOMap.put(insurance.getId() + "",
                    InsuranceDTO.builder()
                            .name(insurance.getName())
                            .guaranteeInfoList(guaranteeInfoStringList)
                            .salesTargetList(salesTargetStringList)
                            .evaluationReportList(evaluationInfo)
                            .build());
        });
//System.out.print(client.getName());
        return ClientDTO
                .builder()
                .name(client.getName())
                .insurances(insuranceDTOMap)
                .build();
    }

    @Override
    public boolean addAccident(int contractId, String accidentArea, AccidentType accidentType, LocalDateTime date){
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
