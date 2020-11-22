package system.insurance.backend.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import system.insurance.backend.client.RegisteredClient;
import system.insurance.backend.contract.Contract;
import system.insurance.backend.employee.Employee;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.insurance.Insurance;
import system.insurance.backend.resource.dto.ClientDTO;
import system.insurance.backend.resource.dto.ContractDTO;
import system.insurance.backend.resource.dto.ContractDetailDTO;
import system.insurance.backend.resource.dto.UWPolicyDTO;
import system.insurance.backend.resource.repository.ContractRepository;
import system.insurance.backend.resource.repository.EmployeeRepository;
import system.insurance.backend.resource.repository.InsuranceRepository;
import system.insurance.backend.resource.repository.UnderWritingPolicyRepository;
import system.insurance.backend.underWriting.UWPolicy;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnderWritingServiceImpl implements UnderWritingService {

    private InsuranceRepository insuranceRepository;
    private UnderWritingPolicyRepository underWritingPolicyRepository;

    private EmployeeRepository employeeRepository;
    private ContractRepository contractRepository;


    @Autowired
    public UnderWritingServiceImpl(InsuranceRepository insuranceRepository, UnderWritingPolicyRepository underWritingPolicyRepository,
    EmployeeRepository employeeRepository, ContractRepository contractRepository) {
        this.insuranceRepository = insuranceRepository;
        this.underWritingPolicyRepository = underWritingPolicyRepository;
        this.employeeRepository=employeeRepository;
        this.contractRepository=contractRepository;
    }

    @Override
    public List<ContractDTO> getContractList(int eid) throws NoEmployeeException {
        Employee employee = this.employeeRepository.findById(eid).orElseThrow(NoEmployeeException::new);
        List<Contract> contractList = this.contractRepository.findAllBySalesPerson(employee);
        List<ContractDTO> contractDTOList = new ArrayList<>();

        contractList.forEach((contract) -> {
            contractDTOList.add(
                    ContractDTO.builder()
                            .id(contract.getId())
                            .clientName(contract.getClient().getName())
                            .insuranceType(contract.getInsurance().getType())
                            .compensationProvision(contract.isCompensationProvision())
                            .count(this.contractRepository.findAllByClientAndSalesPerson(contract.getClient(),employee).toArray().length)
                            .underWritingPassed(contract.isUnderwritingPassed())
                            .build());
        });
        return contractDTOList;
    }

    @Override
    public List<UWPolicyDTO> getUnderWritingPolicyList() {
        //UWPolicy는 무조건 보험마다 하나씩 있어야 하는데, 만약 없으면 새로 만들어준다.
        List<Insurance> insuranceList = this.insuranceRepository.findAll();
        for (Insurance insurance : insuranceList) {
            Optional<UWPolicy> optional = underWritingPolicyRepository.findByInsurance(insurance);
            //존재하는 보험에 uWpolicy가 없는 경우!
            if (!optional.isPresent()) {
                //여기서 만들어준다. 하나.
                this.underWritingPolicyRepository.save(
                        UWPolicy.builder()
                                .insurance(insurance)
                                .environmentalPolicy("미정")
                                .physicalPolicy("미정")
                                .financialPolicy("미정")
                                .date(Date.valueOf(LocalDate.now()))
                                .build()
                );

            }
        }
        /////////////////////////////////////

        List<UWPolicyDTO> uwPolicyDTOList = new ArrayList<>();
        List<UWPolicy> uwPolicyList = this.underWritingPolicyRepository.findAll();
        uwPolicyList.forEach((uwPolicy) -> {
            uwPolicyDTOList.add(
             UWPolicyDTO.builder()
                     .id(uwPolicy.getInsurance().getId())
                     .name(uwPolicy.getInsurance().getName())
                     .date(uwPolicy.getDate())
                     .environmentalPolicy(uwPolicy.getEnvironmentalPolicy())
                     .physicalPolicy(uwPolicy.getPhysicalPolicy())
                     .financialPolicy(uwPolicy.getFinancialPolicy())
                     .build()
            );
//            System.out.println(uwPolicy.getInsurance().getId()+uwPolicy.getInsurance().getName()+uwPolicy.getPhysicalPolicy());
        });
        return uwPolicyDTOList;
    }

    @Override
    public boolean saveFactorsToClient() {
        return false;
    }

    @Override
    public List<ContractDTO> getUnPassedContractList(int id) throws NoEmployeeException {
        List<ContractDTO> contractList = new ArrayList<>();

        Optional<Employee> opt = this.employeeRepository.findById(id);
        if(opt.isPresent()) {
            Employee employee=opt.get();
           List<Contract> contracts= this.contractRepository.findAllBySalesPerson(employee);
           contracts.forEach((contract)->{
               contractList.add(
                       ContractDTO.builder()
                               .id(contract.getId())
                               .clientName(contract.getClient().getName())
                               .insuranceType(contract.getInsurance().getType())
                               .compensationProvision(contract.isCompensationProvision())
                               .count(this.contractRepository.findAllByClientAndSalesPersonAndUnderwritingPassed(contract.getClient(),employee,contract.isUnderwritingPassed()).toArray().length)
                               .underWritingPassed(contract.isUnderwritingPassed())
                               .build());
           });
        }else{
            throw new NoEmployeeException();
        }
        return contractList;
    }

    @Override
    public ResponseEntity<ContractDetailDTO> getContractDetailFactors(int contractId) {
        Optional<Contract> opt =this.contractRepository.findById(contractId);

        if(opt.isPresent()){
            Contract contract = opt.get();
            ContractDetailDTO dto= ContractDetailDTO
                    .builder()
                    .insuranceName(contract.getInsurance().getName())
                    .insuranceType(contract.getInsurance().getType().name())
                    .environmentalDangerousArea(((RegisteredClient)contract.getClient()).getEnvironmentalFactor().getDangerousArea())
                    .environmentalJob(((RegisteredClient)contract.getClient()).getEnvironmentalFactor().getJob().name())
                    .environmentalDangerousHobby(((RegisteredClient)contract.getClient()).getEnvironmentalFactor().getDangerousHobby())
                    .financialCreditRating(((RegisteredClient)contract.getClient()).getFinancialFactor().getCreditRating())
                    .financialIncome(((RegisteredClient)contract.getClient()).getFinancialFactor().getIncome())
                    .financialProperty(((RegisteredClient)contract.getClient()).getFinancialFactor().getProperty())
                    .physicalDrinkingFrequency(((RegisteredClient)contract.getClient()).getPhysicalFactor().getDrinkingFrequency().getDescription())
                    .physicalSmokeFrequency(((RegisteredClient)contract.getClient()).getPhysicalFactor().getSmokeFrequency().getDescription())
                    .build();
            return ResponseEntity.ok(dto);
        }
        return null;
    }


}
