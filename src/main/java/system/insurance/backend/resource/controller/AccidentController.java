package system.insurance.backend.resource.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import system.insurance.backend.accident.AccidentType;
import system.insurance.backend.exception.NoAccidentException;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.resource.service.AccidentService;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;

    @PostMapping("/new_accident/insurance_subscription_check")
    public boolean checkClient(@RequestParam(name = "name") String name, @RequestParam(name = "rrn") String rrn) {
        try {
            return this.accidentService.checkRegisteredClient(name, rrn);
        } catch (NoClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/new_accident/accident_register")
    public boolean registerAccident(@RequestParam(name = "accidentArea") String accidentArea, @RequestParam(name = "accidentType")AccidentType accidentType,
                                        @RequestParam(name = "date")Date date) {
        try {
            return this.accidentService.addAccident(accidentArea, accidentType, date);
        } catch (NoAccidentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
