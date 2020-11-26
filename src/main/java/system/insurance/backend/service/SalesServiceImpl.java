package system.insurance.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.dbo.contract.PremiumPayment;
import system.insurance.backend.dbo.counseling.ClientCounseling;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dbo.instruction.Instruction;
import system.insurance.backend.dbo.instruction.InstructionType;
import system.insurance.backend.dbo.instruction.SalesInstruction;
import system.insurance.backend.dbo.insurance.Insurance;
import system.insurance.backend.dto.InstructionDTO;
import system.insurance.backend.dto.LossRateDTO;
import system.insurance.backend.repository.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class SalesServiceImpl implements SalesService {
    private final SalesInstructionRepository salesInstructionRepository;
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientCounselingRepository clientCounselingRepository;

    private final InsuranceRepository insuranceRepository;

    private final PremiumPaymentRepository premiumPaymentRepository;

    @Autowired
    public SalesServiceImpl(SalesInstructionRepository salesInstructionRepository, ContractRepository contractRepository,
                            EmployeeRepository employeeRepository, InsuranceRepository insuranceRepository,
                            ClientCounselingRepository clientCounselingRepository, PremiumPaymentRepository premiumPaymentRepository
    ) {
        this.salesInstructionRepository = salesInstructionRepository;
        this.contractRepository = contractRepository;
        this.employeeRepository = employeeRepository;
        this.clientCounselingRepository = clientCounselingRepository;
        this.premiumPaymentRepository = premiumPaymentRepository;
        this.insuranceRepository = insuranceRepository;
    }


    @Override
    public boolean instructionRegister(String title, String instruction, int id) throws NoEmployeeException {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(NoEmployeeException::new);
        this.salesInstructionRepository.save(SalesInstruction.builder()
                .author(employee)
                .title(title)
                .instruction(instruction)
                .type(InstructionType.SALES)
                .date(Date.valueOf(LocalDate.now()))
                .build());
        return true;
    }

    @Override
    public List<InstructionDTO> getSalesInstructionList() {
        List<Instruction> salesInstructionList = this.salesInstructionRepository.findAllByType(InstructionType.SALES);
        List<InstructionDTO> instructionDTOList = new ArrayList<>();
        salesInstructionList.forEach((instruction) -> instructionDTOList.add(InstructionDTO.builder()
                .id(instruction.getId())
                .title(instruction.getTitle())
                .authorId(instruction.getAuthor().getId())
                .author(instruction.getAuthor().getName())
                .date(instruction.getDate())
                .build()));
        return instructionDTOList;
    }



    @Override
    public boolean saveCounselingRecord(String content, int eid) throws NoEmployeeException {
        Optional<Employee> employee = this.employeeRepository.findById(eid);
        Employee employee1 = employee.orElseThrow(NoEmployeeException::new);
        this.clientCounselingRepository.save(ClientCounseling.builder()
                .content(content)
                .counselor(employee1)
                .date(Date.valueOf(LocalDate.now())).build());
        return true;
    }

    @Override
    public List<LossRateDTO> getLossRateListFor(int term) {
        List<LossRateDTO> lossRateList = new ArrayList<>();

        List<Insurance> insuranceList = this.insuranceRepository.findAll();
        insuranceList.forEach((insurance) -> {
                    //이거는 회사가 보험금으로 지급한 액수.
                    int given = 10000000;
                    int percent = insurance.getCompany().getSupplementary_insurance_premium_percentage();

                    List<Contract> contractList = this.contractRepository.findAllByInsurance(insurance);

                    //계산을 위한 오늘 Date
                    Date today= Date.valueOf(LocalDate.now());
                    Calendar calendar= new GregorianCalendar(Locale.KOREA);
                    calendar.setTime(today);

                    //기준날짜인 오늘로부터 term개월 전
                    calendar.add(calendar.MONTH,-term);
                    Date target=new java.sql.Date(calendar.getTime().getTime());


                    //고객으로부터 받은 보험료
                    //원래 0으로 계산하는 게 맞으나, 0으로 나누면 ArithmeticException이 발생하여 일단 넣음.
                    int got = 1000;
                    for (Contract contract : contractList) {

                        List<PremiumPayment> premiumPayments = this.premiumPaymentRepository.findAllByContract(contract);
                        for(PremiumPayment premiumPayment: premiumPayments){
                            if(premiumPayment.getDate().after(target)){
                                got+=premiumPayment.getPaidAmount();
                            }
                        }

                    }
//                    System.out.println(got*percent+"하하");
                    float lossRate = given / ((got * percent) / 100);
                    System.out.println(given + "/ ((" + got + "* " + percent + ")/100)");

                    lossRateList.add(
                            LossRateDTO.builder()
                                    .companyName(insurance.getCompany().getCompanyName())
                                    .insuranceName(insurance.getName())
                                    .lossRate(lossRate)
                                    .build()
                    );

                    System.out.println(insurance.getName() + lossRate);
                }
        );
//        Optional<InsuranceCompany> insuranceCompany= insuranceCompanyRepository.


        return lossRateList;
    }
}
