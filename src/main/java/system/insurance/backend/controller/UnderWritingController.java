package system.insurance.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.ContractDTO;
import system.insurance.backend.dto.ContractDetailDTO;
import system.insurance.backend.dto.UWPolicyDTO;
import system.insurance.backend.service.UnderWritingService;

import java.util.List;

@RestController
@RequestMapping("/uw")
public class UnderWritingController {

    private UnderWritingService underWritingService;

    @Autowired
    public UnderWritingController(UnderWritingService underWritingService) {
        this.underWritingService = underWritingService;
    }

    @GetMapping("/uw_policy/list")
    @ResponseBody
    public List<UWPolicyDTO> getUWPolicyDTOList() {
        System.out.println("list");return this.underWritingService.getUnderWritingPolicyList();
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
        System.out.println("ㅇㅇㅇ");
        return this.underWritingService.getContractDetailFactors(contractId);
    }


}
