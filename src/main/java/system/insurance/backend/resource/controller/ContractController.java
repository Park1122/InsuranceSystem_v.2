package system.insurance.backend.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.resource.dto.ContractDTO;
import system.insurance.backend.resource.dto.LossRateDTO;
import system.insurance.backend.resource.service.SalesService;
import system.insurance.backend.resource.service.UnderWritingService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {
    private final SalesService salesService;
    private final UnderWritingService underWritingService;

    @Autowired
    public ContractController(SalesService salesService, UnderWritingService underWritingService) {
        this.salesService = salesService;
        this.underWritingService=underWritingService;
    }

    @GetMapping("/policy_establishment")
    @ResponseBody
    public List<ContractDTO> getContractListByResponsibility(@RequestParam("eid") int eid) {
        try {
            return this.underWritingService.getContractList(eid);
        } catch (NoEmployeeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    @GetMapping("/loss_rate")
    @ResponseBody
    public List<LossRateDTO> getLossRateFor(@RequestParam("term")int term){
        System.out.println("lossRate-"+term);
        return this.salesService.getLossRateListFor(term);
    }


}
