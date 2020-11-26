package system.insurance.backend.dbo.underWriting;

import lombok.*;
import system.insurance.backend.dbo.insurance.Insurance;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="uw_policy")
public class UWPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "insurance_id", referencedColumnName = "id")
    private Insurance insurance;

    private String environmentalPolicy;
    private String physicalPolicy;
    private String financialPolicy;

    private Date date;

    @Builder
    public UWPolicy(Insurance insurance, String environmentalPolicy, String physicalPolicy, String financialPolicy, Date date){
        this.insurance=insurance;
        this.environmentalPolicy=environmentalPolicy;
        this.physicalPolicy=physicalPolicy;
        this.financialPolicy=financialPolicy;
        this.date=date;
    }


}



