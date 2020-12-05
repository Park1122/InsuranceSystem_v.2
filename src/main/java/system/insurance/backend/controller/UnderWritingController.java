package system.insurance.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.dto.*;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.service.ClientService;
import system.insurance.backend.service.UnderWritingService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/uw")
public class UnderWritingController {

    private UnderWritingService underWritingService;
    private ClientService clientService;

    @Autowired
    public UnderWritingController(UnderWritingService underWritingService,ClientService clientService) {
        this.underWritingService = underWritingService;
    this.clientService=clientService;
    }

    @GetMapping("factor/info")
    @ResponseBody
    public ResponseEntity<ClientFactorInfoDTO> getClientFactorInfos(){
        return ResponseEntity.ok(this.clientService.getClientFactorInfos());
    }


    //인수조건이 아직 없고, Contract가 onProgress인 고객목록
    @GetMapping("/uw_policy/getEmptyFactorCustomers")
    @ResponseBody
    public Map<Integer, ClientFactorDTO> getOnProgressContractAndLessFactorCustomers(@RequestParam(name = "eid") int id) {
        return this.underWritingService.getOnProgressContractAndLessFactorCustomers(id);
    }

    //인수조건 리스트
    @GetMapping("/uw_policy/list")
    @ResponseBody
    public List<UWPolicyDTO> getUWPolicyDTOList() {
//        System.out.println("list");
        return this.underWritingService.getUnderWritingPolicyList();
    }

    //인수조건 하나.
    @GetMapping("/uw_policy")
    @ResponseBody
    public Optional<UWPolicyDTO> getUWPolicyDTO(@RequestParam(name = "pid") int pid) {
//        System.out.println("one"+pid);
        return this.underWritingService.getUnderWritingPolicy(pid);
//        return null;
    }


    //저장용도(4)
    @GetMapping("/factor_manage")
    @ResponseBody
    public boolean saveFactorsToClient() {
        return this.underWritingService.saveFactorsToClient();
    }

    //표 표시 용도(1,2)
    @GetMapping("/factor_manage/list")
    @ResponseBody
    public List<ContractDTO> getUnPassedContractDTOList(@RequestParam(name = "eid") int id) {

        try {
            return this.underWritingService.getUnPassedContractList(id);
        } catch (NoEmployeeException e) {
            e.printStackTrace();
            return null;
        }
    }

    //고객의 팩터들 불러오는 용도(3)
    @GetMapping("/factor_manage/client")
    @ResponseBody
    public ResponseEntity<ContractDetailDTO> getClientDTO(@RequestParam(name="contractId") int contractId) {
//        System.out.println("ㅇㅇㅇ");
        return this.underWritingService.getContractDetailFactors(contractId);
    }


}
