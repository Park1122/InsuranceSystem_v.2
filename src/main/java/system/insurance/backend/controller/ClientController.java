package system.insurance.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.dto.ContractDetailDTO;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.ClientDTO;
import system.insurance.backend.service.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final SalesService salesService;
    private final MailService mailService;
    private final UnderWritingService underWritingService;

    public ClientController(ClientService clientService, SalesService salesService, MailService mailService,UnderWritingService underWritingService) {
        this.clientService = clientService;
        this.salesService = salesService;
        this.mailService = mailService;
        this.underWritingService=underWritingService;
    }

    //가망고객을 DB에 등록.
    @PostMapping("/save/unRegistered")
    public boolean saveNewUnRegisteredClient(@RequestParam(name="customerName")String customerName, @RequestParam(name="contact")String contact,
                                             @RequestParam(name="age")int age,@RequestParam(name="email")String email,@RequestParam(name="sex")String sex){
        return this.clientService.saveNewUnRegisteredClient(customerName,contact, age,email,sex );
    }

    //폼을 위한 성별 목록.
    @GetMapping("/sex/list")
    public Map<String,String> getSexList(){
        return clientService.getSexList();
    }

    //고객 요인 저장하기.
    @PostMapping("/save/Factors")
    public void saveClientFactors(@RequestParam(name="cid") int cid,@RequestParam(name="physicalSmokeFrequency") String physicalSmokeFrequency,@RequestParam(name="physicalDrinkingFrequency") String physicalDrinkingFrequency,
                                  @RequestParam(name="environmentalDangerousArea") String environmentalDangerousArea,@RequestParam(name="environmentalDangerousHobby") String environmentalDangerousHobby,@RequestParam(name="environmentalJob") String environmentalJob,
                                  @RequestParam(name="financialIncome") long financialIncome,@RequestParam(name="financialCreditRating") int financialCreditRating,@RequestParam(name="financialProperty") long financialProperty){
        try {
            this.clientService.saveClientFactors(cid, physicalSmokeFrequency, physicalDrinkingFrequency, environmentalDangerousArea, environmentalDangerousHobby, environmentalJob, financialIncome, financialCreditRating, financialProperty);
            this.underWritingService.savePremiumRate(cid);
        } catch (NoClientException e) {
            e.printStackTrace();
        }
    }

    //미등록고객 중 한명.
    @GetMapping("/unregistered/search")
    public ResponseEntity<ClientDTO> findUnregisteredClientbyId(@RequestParam(name = "id")int cid){
        return ResponseEntity.ok(this.clientService.findUnregisteredClientByID(cid));
    }

    //미등록고객 목록.
    @GetMapping("/unregistered/list")
    public List<ClientDTO> findUnregisteredClientList(){
        return this.clientService.findAllUnregisteredClint();
    }

    //등록중인 고객 목록
    @GetMapping("/registering/list")
    public Map<Integer, ClientDTO> findAllRegisteringClientList(){
        return this.clientService.findAllRegisteringClient();
    }

    //아직 승인 안한 계약목록
    @GetMapping("/onProgress/list")
    public Map<Integer, ContractDetailDTO> findAllOnProgressContractList(@RequestParam(name = "eid") int id){
        return this.underWritingService.findAllOnProgressContractList(id);
    }

    //등록죽인 고객 한명.
    @GetMapping("/registering/detail")
    public ResponseEntity<ClientDTO> getRegisteringClientDetail(@RequestParam(name = "id") int id) {
        try {
            return ResponseEntity.ok(this.clientService.getRegisteringClientDetail(id));
        } catch (NoClientException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //등록된 고객을 이름과 주민번호로 찾기.
    @GetMapping("/registered/search")
    public ResponseEntity<ClientDTO> searchRegisteredByNameAndRrn(@RequestParam(name = "rrn") String rrn,@RequestParam(name = "name") String name){
        return ResponseEntity.ok(this.clientService.searchRegisteredByNameAndRrn(rrn,name));
    }

    //등록된 고객을 이름으로 찾기.
    @GetMapping("/registered/search{name}")
    public ResponseEntity<ClientDTO> searchRegisteredByName(@PathVariable String name){
        return ResponseEntity.ok(this.clientService.searchRegisteredByName(name));
    }

    //등록된 고객을 계약으로 찾기.
    @GetMapping("/registered/search{contact}")
    public ResponseEntity<ClientDTO> searchRegisteredByContact(@PathVariable String contact){
        return ResponseEntity.ok(this.clientService.searchRegisteredByContact(contact));
    }

    //등록된 고객을 주민번호로 찾기.
    @GetMapping("/registered/search{rrn}")
    public ResponseEntity<ClientDTO> searchRegisteredByRRN(@PathVariable String rrn){
        return ResponseEntity.ok(this.clientService.searchRegisteredByRRN(rrn));
    }

    //등록된 고객을 이름과 아이디로 찾기
    @GetMapping("/search/nameAndId")
    public ResponseEntity<ClientDTO> searchClient(@RequestParam(name = "name") String name,@RequestParam(name = "id") int id){
        return ResponseEntity.ok(this.clientService.searchClientByIdAndName(id,name));
    }

    //고객 상담 저장.
    @PostMapping("/new/register")
    public boolean newClient(@RequestParam(name = "content") String content, @RequestParam(name = "eid")int eid, @RequestParam(name = "email")String email) {
        try {
            this.mailService.sendMail(email);
            return this.salesService.saveCounselingRecord(content, eid);
        } catch (NoEmployeeException | MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
