package system.insurance.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.ClientDTO;
import system.insurance.backend.service.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/save/Factors")
    public void saveClientFactors(@RequestParam(name="cid") int cid,@RequestParam(name="physicalSmokeFrequency") String physicalSmokeFrequency,@RequestParam(name="physicalDrinkingFrequency") String physicalDrinkingFrequency,
                                  @RequestParam(name="environmentalDangerousArea") String environmentalDangerousArea,@RequestParam(name="environmentalDangerousHobby") String environmentalDangerousHobby,@RequestParam(name="environmentalJob") String environmentalJob,
                                  @RequestParam(name="financialIncome") long financialIncome,@RequestParam(name="financialCreditRating") int financialCreditRating,@RequestParam(name="financialProperty") long financialProperty){
        try {
//            System.out.println("post come");
            this.clientService.saveClientFactors(cid, physicalSmokeFrequency, physicalDrinkingFrequency, environmentalDangerousArea, environmentalDangerousHobby, environmentalJob, financialIncome, financialCreditRating, financialProperty);
            this.underWritingService.savePremiumRate(cid);


        } catch (NoClientException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/unregistered/search")
    public ResponseEntity<ClientDTO> findAllUnregisteredClientList(@RequestParam(name = "id")int cid){
        return ResponseEntity.ok(this.clientService.findUnregisteredClientByID(cid));
    }

    @GetMapping("/unregistered/list")
    public List<ClientDTO> findAllUnregisteredClientList(){
        return this.clientService.findAllUnregisteredClint();
    }

    @GetMapping("/registering/list")
    public Map<Integer, ClientDTO> findAllRegisteringClientList(){
        return this.clientService.findAllRegisteringClient();
    }

    @GetMapping("/registering/detail")
    public ResponseEntity<ClientDTO> getRegisteringClientDetail(@RequestParam(name = "id") int id) {
        try {
            return ResponseEntity.ok(this.clientService.getRegisteringClientDetail(id));
        } catch (NoClientException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/registered/search{name}")
    public ResponseEntity<ClientDTO> searchRegisteredByName(@PathVariable String name){
        return ResponseEntity.ok(this.clientService.searchRegisteredByName(name));
    }

    @GetMapping("/registered/search{contact}")
    public ResponseEntity<ClientDTO> searchRegisteredByContact(@PathVariable String contact){
        return ResponseEntity.ok(this.clientService.searchRegisteredByContact(contact));
    }

    @GetMapping("/registered/search{rrn}")
    public ResponseEntity<ClientDTO> searchRegisteredByRRN(@PathVariable String rrn){
        return ResponseEntity.ok(this.clientService.searchRegisteredByRRN(rrn));
    }

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
