package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsuranceInfoDTO {
    private final Map<String, String> companyList;
    private final Map<String, String> typeList;
    private final Map<Integer, InsuranceDTO> productList;

    @Builder
    public InsuranceInfoDTO(Map<String, String> companyList, Map<String, String> typeList, Map<Integer, InsuranceDTO> productList){
        this.companyList = companyList;
        this.typeList = typeList;
        this.productList = productList;
    }
}
