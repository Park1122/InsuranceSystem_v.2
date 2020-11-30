package system.insurance.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import system.insurance.backend.dbo.client.RegisteredClient;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.dbo.contract.UnderWritingStatus;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.dbo.insurance.InsuranceStatus;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dto.ContractDTO;
import system.insurance.backend.dto.ContractDetailDTO;
import system.insurance.backend.dto.UWPolicyDTO;
import system.insurance.backend.repository.ContractRepository;
import system.insurance.backend.repository.EmployeeRepository;
import system.insurance.backend.repository.InsuranceRepository;
import system.insurance.backend.repository.UnderWritingPolicyRepository;
import system.insurance.backend.dbo.underWriting.UWPolicy;
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
        this.employeeRepository = employeeRepository;
        this.contractRepository = contractRepository;
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
                            .count(this.contractRepository.findAllByClientAndSalesPerson(contract.getClient(), employee).toArray().length)
                            .underWritingPassed(contract.getUnderwritingPassed())
                            .build());
        });
        return contractDTOList;
    }

    //인수 지침 등록화면: 인수 지침 목록을 보여준다. 여기서도 개발중인 보험은 뺀다.
    @Override
    public List<UWPolicyDTO> getUnderWritingPolicyList() {

        //없으면 없는거지 만들지 않는다!!
//        //UWPolicy는 무조건 보험마다 하나씩 있어야 하는데, 만약 없으면 새로 만들어준다.
//        List<Insurance> insuranceList = this.insuranceRepository.findAllByStatus(InsuranceStatus.ON_SALE);
//        for (Insurance insurance : insuranceList) {
//            Optional<UWPolicy> optional = underWritingPolicyRepository.findByInsurance(insurance);
//            //존재하는 보험에 uWpolicy가 없는 경우!
//            if (!optional.isPresent()) {
//                //여기서 만들어준다. 하나.
//                this.underWritingPolicyRepository.save(
//                        UWPolicy.builder()
//                                .insurance(insurance)
//                                .environmentalPolicy("미정")
//                                .physicalPolicy("미정")
//                                .financialPolicy("미정")
//                                .date(LocalDate.now())
//                                .build()
//                );
//
//            }
//        }
        /////////////////////////////////////

        List<Insurance> insuranceList = this.insuranceRepository.findAllByStatus(InsuranceStatus.ON_SALE);

        List<UWPolicyDTO> uwPolicyDTOList = new ArrayList<>();

        insuranceList.forEach((insurance) -> {
            if (insurance.getUwPolicy() != null) {
                UWPolicy uwPolicy = insurance.getUwPolicy();
                uwPolicyDTOList.add(
                        UWPolicyDTO.builder()
                        .uwPolicyId(uwPolicy.getId())
                        .name(insurance.getName())
                        .date(uwPolicy.getDate())
                        .environmentalPolicy(uwPolicy.getEnvironmentalPolicy())
                        .physicalPolicy(uwPolicy.getPhysicalPolicy())
                        .financialPolicy(uwPolicy.getFinancialPolicy())
                        .build()
                );
            }
        });
//        List<UWPolicy> uwPolicyList = this.underWritingPolicyRepository.findAll();
//        uwPolicyList.forEach((uwPolicy) -> {
//            uwPolicyDTOList.add(
//                    UWPolicyDTO.builder()
//                            .uwPolicyId(uwPolicy.getId())
//                            .name(uwPolicy.getInsurance().getName())
//                            .date(uwPolicy.getDate())
//                            .environmentalPolicy(uwPolicy.getEnvironmentalPolicy())
//                            .physicalPolicy(uwPolicy.getPhysicalPolicy())
//                            .financialPolicy(uwPolicy.getFinancialPolicy())
//                            .build()
//            );
//            System.out.println(uwPolicy.getInsurance().getId()+uwPolicy.getInsurance().getName()+uwPolicy.getPhysicalPolicy());
//        });
        return uwPolicyDTOList;
    }

    //하나의 보험의 policy를 찾는다.
    @Override
    public Optional<UWPolicyDTO> getUnderWritingPolicy(int pid) {
//        Optional<UWPolicy> temp = this.underWritingPolicyRepository.findById(pid);
//        if (temp.isPresent()) {
//            UWPolicy uwPolicy = temp.get();
//            UWPolicyDTO uwPolicyDTO = UWPolicyDTO.builder()
//                    .uwPolicyId(uwPolicy.getId())
//                    .name(uwPolicy.getInsurance().getName())
//                    .date(uwPolicy.getDate())
//                    .environmentalPolicy(uwPolicy.getEnvironmentalPolicy())
//                    .physicalPolicy(uwPolicy.getPhysicalPolicy())
//                    .financialPolicy(uwPolicy.getFinancialPolicy())
//                    .build();
////            System.out.println(uwPolicy.getInsurance().getId() + uwPolicy.getInsurance().getName() + uwPolicy.getPhysicalPolicy());
//            return Optional.of(uwPolicyDTO);
//        }
//        System.out.println("없읍");
        return Optional.empty();
    }

    @Override
    public boolean saveFactorsToClient() {
        return false;
    }

    @Override
    public List<ContractDTO> getUnPassedContractList(int id) throws NoEmployeeException {
        List<ContractDTO> contractList = new ArrayList<>();

        Optional<Employee> opt = this.employeeRepository.findById(id);
        if (opt.isPresent()) {
            Employee employee = opt.get();
            List<Contract> contracts = this.contractRepository.findAllBySalesPersonAndUnderwritingPassed(employee, UnderWritingStatus.ONPROGRESS);
            contracts.forEach((contract) -> {
                contractList.add(
                        ContractDTO.builder()
                                .id(contract.getId())
                                .clientName(contract.getClient().getName())
                                .insuranceType(contract.getInsurance().getType())
                                .compensationProvision(contract.isCompensationProvision())
                                .count(this.contractRepository.findAllByClientAndSalesPersonAndUnderwritingPassed(contract.getClient(), employee, contract.getUnderwritingPassed()).toArray().length)
                                .underWritingPassed(contract.getUnderwritingPassed())
                                .build());
            });
        } else {
            throw new NoEmployeeException();
        }
        return contractList;
    }

    @Override
    public ResponseEntity<ContractDetailDTO> getContractDetailFactors(int contractId) {
        Optional<Contract> opt = this.contractRepository.findById(contractId);

        if (opt.isPresent()) {
            Contract contract = opt.get();
            ContractDetailDTO dto = ContractDetailDTO
                    .builder()
                    .insuranceName(contract.getInsurance().getName())
                    .insuranceType(contract.getInsurance().getType().name())
                    .environmentalDangerousArea(((RegisteredClient) contract.getClient()).getEnvironmentalFactor().getDangerousArea())
                    .environmentalJob(((RegisteredClient) contract.getClient()).getEnvironmentalFactor().getJob().getDescription())
                    .environmentalDangerousHobby(((RegisteredClient) contract.getClient()).getEnvironmentalFactor().getDangerousHobby())
                    .financialCreditRating(((RegisteredClient) contract.getClient()).getFinancialFactor().getCreditRating())
                    .financialIncome(((RegisteredClient) contract.getClient()).getFinancialFactor().getIncome())
                    .financialProperty(((RegisteredClient) contract.getClient()).getFinancialFactor().getProperty())
                    .physicalDrinkingFrequency(((RegisteredClient) contract.getClient()).getPhysicalFactor().getDrinkingFrequency().getDescription())
                    .physicalSmokeFrequency(((RegisteredClient) contract.getClient()).getPhysicalFactor().getSmokeFrequency().getDescription())
                    .build();
            return ResponseEntity.ok(dto);
        }
        return null;
    }


}
