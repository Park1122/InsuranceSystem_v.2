package system.insurance.backend.resource.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import system.insurance.backend.resource.dto.UWPolicyDTO;

import java.util.List;

public interface UnderWritingService {

    public List<UWPolicyDTO> getUnderWritingPolicyList();

}
