package system.insurance.backend.dbo.underWriting;

import lombok.*;
import system.insurance.backend.dbo.insurance.Insurance;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="uw_policy")
public class UWPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String environmentalPolicy;
    private String physicalPolicy;
    private String financialPolicy;
    private LocalDate date;

    @Builder
    public UWPolicy( String environmentalPolicy, String physicalPolicy, String financialPolicy, LocalDate date){
        this.environmentalPolicy=environmentalPolicy;
        this.physicalPolicy=physicalPolicy;
        this.financialPolicy=financialPolicy;
        this.date=date;
    }


}



