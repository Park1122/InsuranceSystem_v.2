package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractDetailDTO {

    private final String insuranceName;
    private final String insuranceType;

    private final String physicalPolicy;
    private final String environmentalPolicy;
    private final String financialPolicy;

    private final String physicalSmokeFrequency;
    private final String physicalDrinkingFrequency;

    private final String environmentalJob;
    private final String environmentalDangerousHobby;
    private final String environmentalDangerousArea;

    private final Long financialIncome;
    private final Long financialProperty;
    private final int financialCreditRating;

    private final Long calculatedPayment;

    @Builder
    public ContractDetailDTO(String insuranceName, String insuranceType, String physicalDrinkingFrequency,String physicalSmokeFrequency,
                           String environmentalJob, String environmentalDangerousArea, String environmentalDangerousHobby,
                             int financialCreditRating, Long financialIncome, Long financialProperty,String physicalPolicy,
                             String environmentalPolicy,String financialPolicy,Long calculatedPayment) {
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
        this.physicalPolicy=physicalPolicy;
        this.environmentalPolicy=environmentalPolicy;
        this.financialPolicy=financialPolicy;
        this.calculatedPayment=calculatedPayment;
    }
}
