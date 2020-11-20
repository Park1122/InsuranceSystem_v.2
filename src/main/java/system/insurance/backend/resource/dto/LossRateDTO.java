package system.insurance.backend.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LossRateDTO {

    private final String companyName;
    private final String insuranceName;
    private final float lossRate;

    @Builder
    public LossRateDTO(String companyName, String insuranceName, float lossRate){
        this.companyName=companyName;
        this.insuranceName=insuranceName;
        this.lossRate=lossRate;
    }

}
