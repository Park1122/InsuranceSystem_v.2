package system.insurance.backend.insurance;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class InsuranceCompany {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String company;
    private String companyName;
    private int supplementary_insurance_premium_percentage;

    @Builder
    public InsuranceCompany(String company, String companyName, int supplementary_insurance_premium_percentage){
        this.company=company;
        this.companyName=companyName;
        this.supplementary_insurance_premium_percentage=supplementary_insurance_premium_percentage;
    }


}
