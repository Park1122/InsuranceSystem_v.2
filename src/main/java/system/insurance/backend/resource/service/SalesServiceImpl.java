package system.insurance.backend.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.insurance.backend.client.Client;
import system.insurance.backend.client.EnvironmentalFactor;
import system.insurance.backend.client.FinancialFactor;
import system.insurance.backend.client.PhysicalFactor;
import system.insurance.backend.contract.Contract;
import system.insurance.backend.counseling.ClientCounseling;
import system.insurance.backend.employee.Employee;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.instruction.Instruction;
import system.insurance.backend.instruction.InstructionType;
import system.insurance.backend.instruction.SalesInstruction;
import system.insurance.backend.insurance.Insurance;
import system.insurance.backend.resource.dto.ContractDTO;
import system.insurance.backend.resource.dto.InstructionDTO;
import system.insurance.backend.resource.repository.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {
    private final SalesInstructionRepository salesInstructionRepository;
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientCounselingRepository clientCounselingRepository;

    private final EnvironmentalFactorRepository environmentalFactorRepository;

    @Autowired
    public SalesServiceImpl(SalesInstructionRepository salesInstructionRepository, ContractRepository contractRepository,
                            EmployeeRepository employeeRepository, ClientCounselingRepository clientCounselingRepository
            , EnvironmentalFactorRepository environmentalFactorRepository) {
        this.salesInstructionRepository = salesInstructionRepository;
        this.contractRepository = contractRepository;
        this.employeeRepository = employeeRepository;
        this.clientCounselingRepository = clientCounselingRepository;

        this.environmentalFactorRepository = environmentalFactorRepository;
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
        System.out.println(salesInstructionList.toString());
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
    public List<ContractDTO> getContractList(int eid) throws NoEmployeeException {
        Employee employee = this.employeeRepository.findById(eid).orElseThrow(NoEmployeeException::new);
        System.out.println(employee.getId() + " | " + employee.getName() + "|" + employee.getAuthority());

        List<Contract> contractList = this.contractRepository.findAllBySalesPerson(employee);
//        List<Client> clientList = this.clientRepository.findAll();

        //invalid Stream header
            List<EnvironmentalFactor> list = this.environmentalFactorRepository.findAll();

        //정상
//            List<FinancialFactor> list = this.financialFactorRepository.findAll();
        //정상
//        List<PhysicalFactor> list = this.physicalFactorRepository.findAll();
        List<ContractDTO> contractDTOList = new ArrayList<>();



        contractList.forEach((contract) -> {
            contractDTOList.add(
                    ContractDTO.builder()
                            .id(contract.getId())
                            .insuranceType(contract.getInsurance().getType())
                            .compensationProvision(contract.isCompensationProvision())
                            .count(this.contractRepository.findAllByClient(contract.getClient()).toArray().length)
                            .build());
        });
        return contractDTOList;
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

//    @Override
//    public List<ContractDTO> getAllContractList() {
//
//        Optional<Contract> contract = this.contractRepository.findAll();
//        return null;
//    }
}
