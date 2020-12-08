package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import system.insurance.backend.dbo.contract.UnderWritingStatus;
import system.insurance.backend.dbo.insurance.InsuranceType;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractDTO {

    private final int id;
    private final int clientId;
    private final String clientName;

    private final InsuranceType insuranceType;
    private final boolean compensationProvision;
    private final int count;

    private final UnderWritingStatus underWritingPassed;
    private final String insuranceName;
    private final Long insurancePayment;


    @Builder
    public ContractDTO(int id,int clientId, String clientName,
                       InsuranceType insuranceType, boolean compensationProvision, int count,
                       UnderWritingStatus underWritingPassed,String insuranceName,Long insurancePayment) {
        this.id = id;
        this.clientId=clientId;
        this.clientName=clientName;

        this.insuranceType = insuranceType;
        this.compensationProvision = compensationProvision;
        this.count = count;

        this.underWritingPassed=underWritingPassed;
        this.insuranceName=insuranceName;
        this.insurancePayment=insurancePayment;

    }

}
