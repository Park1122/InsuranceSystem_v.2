package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UWPolicyDTO {

    private final int uwPolicyId;
    private final String name;
    private final LocalDate date;
    private final String physicalPolicy;
    private final String environmentalPolicy;
    private final String financialPolicy;


    @Builder
    public UWPolicyDTO(int uwPolicyId, String name, LocalDate date, String physicalPolicy, String environmentalPolicy,String financialPolicy) {
        this.uwPolicyId = uwPolicyId;
        this.name=name;
        this.date=date;
        this.physicalPolicy=physicalPolicy;
        this.environmentalPolicy=environmentalPolicy;
        this.financialPolicy=financialPolicy;
    }

}
