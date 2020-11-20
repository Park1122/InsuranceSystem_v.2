package system.insurance.backend.resource.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.accident.AccidentType;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.resource.dto.ContractDTO;
import system.insurance.backend.resource.service.AccidentService;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/new_accident/insurance_subscription_check")
    public List<ContractDTO> checkClient(@RequestParam(name = "name") String name, @RequestParam(name = "rrn") String rrn) {
        try {
            return this.accidentService.checkRegisteredClient(name, rrn);
        } catch (NoClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/new_accident/accident_register")
    public boolean registerAccident(@RequestParam(name = "contractId")int contractId,@RequestParam(name = "accidentArea") String accidentArea,
                                    @RequestParam(name = "accidentType")AccidentType accidentType, @RequestParam(name = "date")Date date) {
        return this.accidentService.addAccident(contractId, accidentArea, accidentType, date);
    }
}
