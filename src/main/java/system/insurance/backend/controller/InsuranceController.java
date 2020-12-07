package system.insurance.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import system.insurance.backend.dto.AuthorizationReportDTO;
import system.insurance.backend.dto.DevelopingInsuranceDTO;
import system.insurance.backend.dto.InsuranceDTO;
import system.insurance.backend.dto.InsuranceInfoDTO;
import system.insurance.backend.service.InsuranceService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {
    private final InsuranceService insuranceService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    // 보험 작성을 위한 폼 용 정보들 묶음.
    @GetMapping("/info")
    @ResponseBody
    public InsuranceInfoDTO getInsuranceInfoList() {
        return this.insuranceService.getInsuranceInfoList();
    }

    //개발중인 보험 목록.
    @GetMapping("/product/developing")
    public List<DevelopingInsuranceDTO> getDevelopingInsuranceList() {
        return this.insuranceService.getDevelopingInsuranceList();
    }

    //판매중인 보험 목록.
    @GetMapping("/product/onSale")
    public Map<String,InsuranceDTO> getOnSaleInsuranceList() {
        return this.insuranceService.getOnSaleInsuranceList();
    }

    //하나의 보험 정보.
    @GetMapping("/product/detail")
    public ResponseEntity<InsuranceDTO> getInsuranceDetails(@RequestParam(name = "id") int id){
        return ResponseEntity.of(this.insuranceService.getInsuranceDetails(id));
    }

    //인가보고서 목록.
    @GetMapping("/authorize")
    public List<AuthorizationReportDTO> getAuthorizationReportList(){
        return this.insuranceService.getAuthorizationReportList();
    }

    //인가보고서 업로드.
    @PostMapping("/authorize/upload")
    public ResponseEntity<Boolean> uploadAuthorizationDoc(@RequestParam(name = "file") MultipartFile file,
                                                          @RequestParam(name="author_eid")Integer author_eid) {
        try{
            return ResponseEntity.ok(this.insuranceService.uploadAuthorizationDoc(file, author_eid));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }

    //evaluation report 업로드.
    @PostMapping("/evaluation")
    public ResponseEntity<Boolean> uploadEvaluationReport(@RequestParam(name = "report") List<MultipartFile> files,
                                                          @RequestParam(name = "insuranceId") int id) {
        try {
            return ResponseEntity.ok(this.insuranceService.uploadEvaluationReport(files, id));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }

    //evaluation report 다운로드.
    @GetMapping("/evaluation")
    public FileSystemResource downloadEvaluationReport(@RequestParam(name = "id")int id, HttpServletResponse res){
        res.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment");
        try {
            return new FileSystemResource(this.insuranceService.downloadEvaluationReport(id));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //새로운 보험 설계하여 등록하기.
    @PostMapping("/product/design")
    public boolean insuranceDesign(@RequestParam(name = "eid") int eid, @RequestParam(name = "type") String type, @RequestParam(name = "name") String name,  @RequestParam(name = "limit") List<Long> limit, @RequestParam(name = "condition") List<String> condition, @RequestParam(name = "special")List<Boolean> special, @RequestParam(name = "targetClient")List<String> targetClient) {
        return this.insuranceService.insuranceDesign(eid, type, name, limit, condition, special, targetClient);
    }
}
