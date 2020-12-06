package system.insurance.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.client.Job;
import system.insurance.backend.dbo.client.RegisteredClient;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.dbo.contract.UnderWritingStatus;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.dbo.insurance.InsuranceStatus;
import system.insurance.backend.dbo.insurance.InsuranceType;
import system.insurance.backend.dto.*;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.repository.*;
import system.insurance.backend.dbo.underWriting.UWPolicy;

import java.time.LocalDate;
import java.util.*;

@Service
public class UnderWritingServiceImpl implements UnderWritingService {

    private InsuranceRepository insuranceRepository;
    private UnderWritingPolicyRepository underWritingPolicyRepository;

    private EmployeeRepository employeeRepository;
    private ContractRepository contractRepository;

    private ClientRepository clientRepository;


    @Autowired
    public UnderWritingServiceImpl(InsuranceRepository insuranceRepository, UnderWritingPolicyRepository underWritingPolicyRepository,
                                   EmployeeRepository employeeRepository, ContractRepository contractRepository, ClientRepository clientRepository) {
        this.insuranceRepository = insuranceRepository;
        this.underWritingPolicyRepository = underWritingPolicyRepository;
        this.employeeRepository = employeeRepository;
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void saveContractStatus(int contractId, String status) {
        Optional<Contract> temp = this.contractRepository.findById(contractId);
        if (temp.isPresent()) {
            Contract contract = temp.get();
            contract.setUnderwritingPassed(UnderWritingStatus.valueOf(status));
            this.contractRepository.save(contract);
        }
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
                                .build()
                );
            }
        });
        return uwPolicyDTOList;
    }

    //하나의 보험의 policy를 찾는다.
    @Override
    public Optional<UWPolicyDTO> getUnderWritingPolicy(int pid) {
        UWPolicyDTO uwPolicyDTO=null;

        Optional<UWPolicy> temp = this.underWritingPolicyRepository.findById(pid);
        if (temp.isPresent()) {
            UWPolicy uwPolicy = temp.get();
            Optional<Insurance> tempp= this.insuranceRepository.findByUwPolicy(uwPolicy);
            if(tempp.isPresent()) {
                Insurance insurance = tempp.get();

                uwPolicyDTO = UWPolicyDTO.builder()
                        .uwPolicyId(uwPolicy.getId())
                        .name(insurance.getName())
                        .date(uwPolicy.getDate())
                        .environmentalPolicy(uwPolicy.getEnvironmentalPolicy())
                        .physicalPolicy(uwPolicy.getPhysicalPolicy())
                        .financialPolicy(uwPolicy.getFinancialPolicy())
                        .build();
//            System.out.println(uwPolicy.getInsurance().getId() + uwPolicy.getInsurance().getName() + uwPolicy.getPhysicalPolicy());

            }
        }

        return Optional.of(uwPolicyDTO);
    }

    @Override
    public Map<Integer, ClientFactorDTO> getOnProgressContractAndLessFactorCustomers(int id) {
        Optional<Employee> opt = this.employeeRepository.findById(id);

        Map<Integer, ClientFactorDTO> dtoMap = new HashMap<Integer, ClientFactorDTO>();
        if (opt.isPresent()) {
            Employee employee = opt.get();
            List<Contract> contracts = this.contractRepository.findAllBySalesPersonAndUnderwritingPassed(employee, UnderWritingStatus.ONPROGRESS);
            contracts.forEach((contract) -> {
                        if (contract.getClient() instanceof RegisteredClient) {
                            RegisteredClient client = (RegisteredClient) contract.getClient();
                            if (client.getEnvironmentalFactor() == null &&
                                    client.getFinancialFactor() == null &&
                                    client.getPhysicalFactor() == null) {
                                dtoMap.put(client.getId(),
                                        ClientFactorDTO.builder()
                                                .clientId(client.getId())
                                                .insuranceType(contract.getInsurance().getType().getDescription())
                                                .insuranceName(contract.getInsurance().getName())
                                                .build());
                            }

                        }

                    }
            );
        }
        return null;
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

                if (contract.getClient() instanceof RegisteredClient) {
                    RegisteredClient client = (RegisteredClient) contract.getClient();
                    if (!(client.getEnvironmentalFactor() == null ||
                            client.getFinancialFactor() == null ||
                            client.getPhysicalFactor() == null)) {
                        contractList.add(
                                ContractDTO.builder()
                                        .id(contract.getId())
                                        .clientName(contract.getClient().getName())
                                        .insuranceType(contract.getInsurance().getType())
                                        .compensationProvision(contract.isCompensationProvision())
                                        .count(this.contractRepository.findAllByClientAndSalesPersonAndUnderwritingPassed(contract.getClient(), employee, contract.getUnderwritingPassed()).toArray().length)
                                        .underWritingPassed(contract.getUnderwritingPassed())
                                        .build());
                    }
                }

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
            ContractDetailDTO dto = ContractDetailDTO.builder()
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

    @Override
    public void savePremiumRate(int cid) {
        Optional<Client> temp = this.clientRepository.findById(cid);
        if (temp.isPresent() && temp.get() instanceof RegisteredClient) {
            RegisteredClient client = (RegisteredClient) temp.get();
            List<Contract> contractList = this.contractRepository.findAllByClient(client);
            for (Contract contract : contractList) {
                Long premiumRate = this.calculatePremiumRate(contract.getInsurance(), client.getEnvironmentalFactor().getJob());
                contract.setPayment(premiumRate);
                this.contractRepository.save(contract);
                ;
            }


        }
    }

    @Override
    public Long calculatePremiumRate(Insurance insurance, Job clientJob) {
        InsuranceType insuranceType = insurance.getType();
        Long payIn = insurance.getBasicPremium();

        Long calculatePay = 0L;
        if (insuranceType.equals(InsuranceType.FIRE)) {
            calculatePay = (long) Math.round(payIn * this.firePremiumRate(clientJob));
        } else if (insuranceType.equals(InsuranceType.INJURY)) {
            calculatePay = (long) Math.round(payIn * this.injuryPremiumRate(clientJob));
        } else if (insuranceType.equals(InsuranceType.DEATH)) {
            calculatePay = (long) Math.round(payIn * this.deathPremiumRate(clientJob));
        }
        return calculatePay;
    }

    @Override
    public Map<Integer, ContractDetailDTO> findAllOnProgressContractList(int cid) {

        Map<Integer, ContractDetailDTO> contractDetailDTOMap = new HashMap<>();

        Optional<Employee> temp = this.employeeRepository.findById(cid);
        if (temp.isPresent()) {
            Employee employee = temp.get();
            List<Contract> contracts = this.contractRepository.findAllBySalesPersonAndUnderwritingPassed(employee, UnderWritingStatus.ONPROGRESS);

            for (Contract contract : contracts) {
                if (contract.getClient() instanceof RegisteredClient) {
                    RegisteredClient client = (RegisteredClient) contract.getClient();

                    if (contract.getInsurance().getUwPolicy() != null && client.getEnvironmentalFactor() != null && client.getPhysicalFactor() != null && client.getFinancialFactor() != null)
                        contractDetailDTOMap.put(
                                contract.getId(),
                                ContractDetailDTO.builder()
                                        .insuranceName(contract.getInsurance().getName())
                                        .insuranceType(contract.getInsurance().getType().getDescription())
                                        .physicalPolicy(contract.getInsurance().getUwPolicy().getPhysicalPolicy())
                                        .environmentalPolicy(contract.getInsurance().getUwPolicy().getEnvironmentalPolicy())
                                        .financialPolicy(contract.getInsurance().getUwPolicy().getFinancialPolicy())

                                        .physicalSmokeFrequency(client.getPhysicalFactor().getSmokeFrequency().getDescription())
                                        .physicalDrinkingFrequency(client.getPhysicalFactor().getDrinkingFrequency().getDescription())

                                        .environmentalDangerousArea(client.getEnvironmentalFactor().getDangerousArea())
                                        .environmentalDangerousHobby(client.getEnvironmentalFactor().getDangerousHobby())
                                        .environmentalJob(client.getEnvironmentalFactor().getJob().getDescription())

                                        .financialCreditRating(client.getFinancialFactor().getCreditRating())
                                        .financialIncome(client.getFinancialFactor().getIncome())
                                        .financialProperty(client.getFinancialFactor().getProperty())

                                        .calculatedPayment(contract.getPayment())
                                        .build()
                        );
                }
            }
        }
        return contractDetailDTOMap;
    }

    @Override
    public void savePolicyInsurance(int insuranceId, String physicalFactor, String financialFactor, String environmentalFactor) {
        Optional<Insurance> temp = this.insuranceRepository.findById(insuranceId);
        if (temp.isPresent()) {
            Insurance insurance = temp.get();
            UWPolicy uwPolicy = UWPolicy.builder().environmentalPolicy(environmentalFactor).physicalPolicy(physicalFactor).financialPolicy(financialFactor).date(LocalDate.now()).build();
            insurance.setUwPolicy(uwPolicy);

            this.underWritingPolicyRepository.save(uwPolicy);
            this.insuranceRepository.save(insurance);
        }
    }


    private float firePremiumRate(Job clientJob) {
        float rate = 1.0f;
        switch (clientJob) {
            case DRIVER:
            case OFFICE_WORKER:
                rate *= 1.2;
                break;
            case HOUSEWIFE:
                rate *= 1.1;
                break;
            case STUDENT:
            case SOLDIER:
            case NONE:
                rate *= 1.0;
                break;
            case SELF_EMPLOYMENT:
                rate *= 1.4;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clientJob);
        }
        return rate;
    }

    private float injuryPremiumRate(Job clientJob) {
        float rate = 1.0f;
        switch (clientJob) {
            case DRIVER:
            case SELF_EMPLOYMENT:
                rate *= 1.2;
                break;
            case HOUSEWIFE:
            case OFFICE_WORKER:
                rate *= 1.1;
                break;
            case STUDENT:
            case NONE:
                rate *= 1.0;
                break;
            case SOLDIER:
                rate *= 1.3;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clientJob);
        }
        return rate;
    }

    private float deathPremiumRate(Job clientJob) {
        float rate = 1.0f;
        switch (clientJob) {
            case DRIVER:
                rate *= 1.3;
                break;
            case HOUSEWIFE:
                rate *= 1.1;
                break;
            case STUDENT:
            case NONE:
                rate *= 1.0;
                break;
            case SOLDIER:
                rate *= 1.4;
                break;
            case OFFICE_WORKER:
            case SELF_EMPLOYMENT:
                rate *= 1.2;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clientJob);
        }
        return rate;
    }


}
