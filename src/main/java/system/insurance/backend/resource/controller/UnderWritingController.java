package system.insurance.backend.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import system.insurance.backend.resource.dto.UWPolicyDTO;
import system.insurance.backend.resource.service.UnderWritingService;
import system.insurance.backend.underWriting.UWPolicy;

import java.util.List;

@RestController
@RequestMapping("/uw")
public class UnderWritingController {

    private UnderWritingService underWritingService;

    @Autowired
    public UnderWritingController(UnderWritingService underWritingService){
        this.underWritingService=underWritingService;
    }

    @GetMapping("/uwPolicy/list")
    @ResponseBody
    public List<UWPolicyDTO> getUWPolicyDTOList(){
        return this.underWritingService.getUnderWritingPolicyList();
    }



}
