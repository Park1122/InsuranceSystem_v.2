package system.insurance.backend.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractDetailDTO {

    private final String insuranceName;
    private final String insuranceType;

    private final String physicalSmokeFrequency;
    private final String physicalDrinkingFrequency;

    private final String environmentalJob;
    private final String environmentalDangerousHobby;
    private final String environmentalDangerousArea;

    private final Long financialIncome;
    private final Long financialProperty;
    private final int financialCreditRating;

    @Builder
    public ContractDetailDTO(String insuranceName, String insuranceType, String physicalDrinkingFrequency,String physicalSmokeFrequency,
                           String environmentalJob, String environmentalDangerousArea, String environmentalDangerousHobby,
                             int financialCreditRating, Long financialIncome, Long financialProperty) {
        this.insuranceName = insuranceName;
        this.insuranceType = insuranceType;
        this.physicalDrinkingFrequency=physicalDrinkingFrequency;
        this.physicalSmokeFrequency=physicalSmokeFrequency;
        this.environmentalDangerousArea=environmentalDangerousArea;
        this.environmentalDangerousHobby=environmentalDangerousHobby;
        this.environmentalJob=environmentalJob;
        this.financialCreditRating=financialCreditRating;
        this.financialIncome=financialIncome;
        this.financialProperty=financialProperty;
    }


}
