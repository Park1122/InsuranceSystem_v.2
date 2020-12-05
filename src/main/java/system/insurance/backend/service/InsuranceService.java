package system.insurance.backend.service;

import org.springframework.web.multipart.MultipartFile;
import system.insurance.backend.dbo.client.Job;
import system.insurance.backend.dto.AuthorizationReportDTO;
import system.insurance.backend.dto.DevelopingInsuranceDTO;
import system.insurance.backend.dto.InsuranceDTO;
import system.insurance.backend.dto.InsuranceInfoDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InsuranceService {
    Map<String, String> getInsuranceCompanyList();
    Map<String, String> getInsuranceTypeList();
    Map<Integer, InsuranceDTO> getProductList();
    List<DevelopingInsuranceDTO> getDevelopingInsuranceList();
    Optional<InsuranceDTO> getInsuranceDetails(int id);
    boolean uploadAuthorizationDoc(MultipartFile file, Integer author_id) throws IOException;
    boolean uploadEvaluationReport(List<MultipartFile> files, int insuranceId) throws IOException;

    File downloadEvaluationReport(int id) throws IOException;

    boolean insuranceDesign(int eid, String type, String name, List<Long> limit, List<String> condition, List<Boolean> special, List<String> targetClient);

    List<AuthorizationReportDTO> getAuthorizationReportList();

    Long calculatePremiumRate(String type, Long payIn, Job clientJob);

    InsuranceInfoDTO getInsuranceInfoList();
}
