package system.insurance.backend.service;

import org.springframework.stereotype.Service;
import system.insurance.backend.dbo.client.*;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.dbo.contract.UnderWritingStatus;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.dto.ClientFactorDTO;
import system.insurance.backend.dto.ClientFactorInfoDTO;
import system.insurance.backend.exception.InvalidIdentifierException;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.dto.ClientDTO;
import system.insurance.backend.repository.*;

import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final RegisteredClientRepository registeredClientRepository;
    private final ContractRepository contractRepository;

    private final PhysicalFactorRepository physicalFactorRepository;
    private final EnvironmentalFactorRepository environmentalFactorRepository;
    private final FinancialFactorRepository financialFactorRepository;
    private final EmployeeRepository employeeRepository;

    public ClientServiceImpl(EmployeeRepository employeeRepository,ClientRepository clientRepository, RegisteredClientRepository registeredClientRepository, ContractRepository contractRepository,PhysicalFactorRepository physicalFactorRepository,EnvironmentalFactorRepository environmentalFactorRepository,FinancialFactorRepository financialFactorRepository) {
        this.clientRepository = clientRepository;
        this.registeredClientRepository = registeredClientRepository;
        this.contractRepository = contractRepository;
        this.physicalFactorRepository=physicalFactorRepository;
        this.environmentalFactorRepository=environmentalFactorRepository;
        this.financialFactorRepository=financialFactorRepository;
        this.employeeRepository=employeeRepository;
    }

    @Override
    public Map<Integer, ClientDTO> findAllRegisteringClient() {
        List<Client> clientList = this.clientRepository.findAllByType(ClientType.REGISTERING);
        Map<Integer, ClientDTO> clientDTOList = new HashMap<>();
        clientList.forEach(client -> {
            RegisteringClient client1 = (RegisteringClient) client;
            clientDTOList.put(client1.getId(), ClientDTO.builder().name(client1.getName()).build());
        });
        return clientDTOList;
    }

    @Override
    public List<ClientDTO> findAllUnregisteredClint() {
        List<Client> clientList = this.clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>() ;
        clientList.forEach((client)-> {
            if(client instanceof NotRegisteredClient) {
                System.out.println(client.getName()+" 가망고객님.");

                String gift="";
                if(((NotRegisteredClient) client).getGift()!=null) gift= ((NotRegisteredClient) client).getGift().getGift();
                clientDTOList.add(
                        ClientDTO.builder()
                                .name(client.getName())
                                .id(client.getId())
                                .age(client.getAge())
                                .sex(client.getSex().getDesc())
                                .gift(gift)
                                .build());
            }
        });
        return clientDTOList;
    }

    @Override
    public ClientDTO findUnregisteredClientByID(int cid) {
        Optional<Client> client = this.clientRepository.findById(cid);
        if(client.isPresent()) {
         Client client1= client.get();
            return ClientDTO
                    .builder()
                    .contact(client1.getContact())
                    .age(client1.getAge())
                    .sex(client1.getSex().getDesc())
                    .name(client1.getName())
                    .email(client1.getEmail())
                    .build();
        }
        return null;
    }

    @Override
    public List<Client> getOnProgressContractAndLessFactorCustomers(int id) {
        Optional<Employee> opt = this.employeeRepository.findById(id);

        List<Client> clientList = new ArrayList<>();
        if (opt.isPresent()) {
            Employee employee = opt.get();
            List<Contract> contracts = this.contractRepository.findAllBySalesPersonAndUnderwritingPassed(employee, UnderWritingStatus.ONPROGRESS);
            contracts.forEach((contract) -> {
                        if (contract.getClient() instanceof RegisteredClient) {
                            RegisteredClient client = (RegisteredClient) contract.getClient();
                            if (client.getEnvironmentalFactor() == null ||
                                    client.getFinancialFactor() == null ||
                                    client.getPhysicalFactor() == null) {
                               clientList.add(client);
                            }
                        }
                    }
            );
        }
        return clientList;
    }

    @Override
    public ClientFactorInfoDTO getClientFactorInfos(int eid) {

        Map<String,String> clientList = new HashMap<>();
        for(Client client: this.getOnProgressContractAndLessFactorCustomers(eid)){
            clientList.put(client.getId()+"", client.getName()+" "+client.getSex());
        }

        Map<String,String> smokeList = new HashMap<>();
        for(SmokeFrequency smokeFrequency: SmokeFrequency.values()){
            smokeList.put(smokeFrequency.name(), smokeFrequency.getDescription());
        }

        Map<String,String> drinkList = new HashMap<>();
        for(DrinkingFrequency drinkingFrequency: DrinkingFrequency.values()){
            drinkList.put(drinkingFrequency.name(), drinkingFrequency.getDescription());
        }

        Map<String,String> jobList = new HashMap<>();
        for(Job job: Job.values()){
            jobList.put(job.name(), job.getDescription());
        }

        return ClientFactorInfoDTO.builder().clientList(clientList).smokeList(smokeList).drinkList(drinkList).jobList(jobList).build();
    }

    @Override
    public void saveClientFactors(int cid, String physicalSmokeFrequency, String physicalDrinkingFrequency, String environmentalDangerousArea, String environmentalDangerousHobby, String environmentalJob, long financialIncome, int financialCreditRating, long financialProperty) throws NoClientException{
        Optional<Client> client = this.clientRepository.findById(cid);
        RegisteringClient client1 = (RegisteredClient) client.orElseThrow(NoClientException::new);

        //////////
        SmokeFrequency smokeFrequency=null;
        for(SmokeFrequency sFreq : SmokeFrequency.values()){
            if (sFreq.name().equals(physicalSmokeFrequency)){smokeFrequency=sFreq;}
        }
        DrinkingFrequency drinkingFrequency=null;
        for(DrinkingFrequency dFreq: DrinkingFrequency.values()){
            if(dFreq.name().equals(physicalDrinkingFrequency)){drinkingFrequency=dFreq;}
        }
        PhysicalFactor physicalFactor= PhysicalFactor.builder().smokeFrequency(smokeFrequency).drinkingFrequency(drinkingFrequency).build();
        this.physicalFactorRepository.save(physicalFactor);
        client1.setPhysicalFactor(physicalFactor);

        //////////
        Job job=null;
        for(Job j: Job.values()){
            if(j.name().equals(environmentalJob)){job=j;}
        }
        EnvironmentalFactor environmentalFactor = EnvironmentalFactor.builder().dangerousArea(environmentalDangerousArea).dangerousHobby(environmentalDangerousHobby).job(job).build();
        this.environmentalFactorRepository.save(environmentalFactor);
        client1.setEnvironmentalFactor(environmentalFactor);

        //////////
        FinancialFactor financialFactor = FinancialFactor.builder().income(financialIncome).creditRating(financialCreditRating).property(financialProperty).build();
        this.financialFactorRepository.save(financialFactor);
        client1.setFinancialFactor(financialFactor);

        this.clientRepository.save(client1);

    }

    @Override
    public Map<String, String> getSexList() {
        Map<String,String> map = new HashMap<>();
        for(Sex sex: Sex.values()){
            map.put(sex.name(),sex.getDesc());
        }
        return map;
    }

    @Override
    public boolean saveNewUnRegisteredClient(String customerName, String contact, int age, String email, String sex) {
        Sex sex1= Sex.valueOf(sex);
        this.clientRepository.save(NotRegisteredClient.builder().name(customerName).contact(contact).age(age).email(email).sex(sex1).build());
        return true;
    }

    @Override
    public ClientDTO searchClientByIdAndName(int id, String name) {
        ClientDTO clientDTO = null;
        Optional<Client> temp =this.clientRepository.findByIdAndName(id,name);
        if(temp.isPresent()) {
            Client client = temp.get();
            clientDTO=ClientDTO.builder().id(client.getId()).name(client.getName()).build();
        }
        return clientDTO;
        }

    @Override
    public ClientDTO getRegisteringClientDetail(int cid) throws NoClientException {
        Optional<Client> client = this.clientRepository.findById(cid);
        RegisteringClient client1 = (RegisteringClient) client.orElseThrow(NoClientException::new);
        Optional<Contract> contract = this.contractRepository.findByClient(client1);
        Contract contract1 = contract.orElseThrow(InvalidIdentifierException::new);
        return ClientDTO.builder()
                .id(client1.getId())
                .age(client1.getAge())
                .contact(client1.getContact())
                .insuranceName(contract1.getInsurance().getName())
                .name(client1.getName())
                .underWritingScore(client1.getUnderWritingScore())
                .build();
    }

    @Override
    public boolean setConformity(int cid, boolean conformity, String reason) {
        Optional<Client> client = this.clientRepository.findById(cid);
        client.ifPresent((client1 -> {
            RegisteringClient client2 = (RegisteringClient) client1;
            client2.setConformity(conformity);
            client2.setReason(reason);
            this.clientRepository.save(client2);
        }));
        return true;
    }

    @Override
    public ClientDTO searchRegisteredByName(String name) {
        Optional<Client> client = this.registeredClientRepository.findByName(name);
        if (client.isPresent()) return getClientDTO(client.get());
        return ClientDTO.builder().build();
    }

    @Override
    public ClientDTO searchRegisteredByContact(String contact) {
        Optional<Client> client = this.registeredClientRepository.findByContact(contact);
        if (client.isPresent()) return getClientDTO(client.get());
        return ClientDTO.builder().build();
    }

    @Override
    public ClientDTO searchRegisteredByRRN(String rrn) {
        Optional<Client> client = this.registeredClientRepository.findByRrn(rrn);
        if (client.isPresent()) return getClientDTO(client.get());
        return ClientDTO.builder().build();
    }



    private ClientDTO getClientDTO(Client client) {
        RegisteredClient registeredClient = (RegisteredClient) client;
        Optional<Contract> contractOptional = this.contractRepository.findByClient(registeredClient);
        Contract contract = contractOptional.orElseThrow(InvalidIdentifierException::new);
        return ClientDTO.builder()
                .id(registeredClient.getId())
                .name(registeredClient.getName())
                .insuranceName(contract.getInsurance().getName())
                .contact(registeredClient.getContact()).build();
    }
}
