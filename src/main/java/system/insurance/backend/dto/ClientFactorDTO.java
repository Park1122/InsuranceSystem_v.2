package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientFactorDTO {
    private final int clientId;
    private final String insuranceName;
    private final String insuranceType;
    private final Integer underWritingScore;
    private final String physicalSmokeFrequency;
    private final String physicalDrinkingFrequency;

    private final String environmentalJob;
    private final String environmentalDangerousHobby;
    private final String environmentalDangerousArea;

    private final Long financialIncome;
    private final Long financialProperty;
    private final int financialCreditRating;
}
