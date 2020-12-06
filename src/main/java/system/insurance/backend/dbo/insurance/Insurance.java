package system.insurance.backend.dbo.insurance;

import lombok.*;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.dbo.underWriting.UWPolicy;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(value = EnumType.STRING)
    private InsuranceType type;
    @Enumerated(value = EnumType.STRING)
    private InsuranceStatus status;
    @ManyToOne(targetEntity = InsuranceCompany.class)
    @JoinColumn(name="insurance_company", referencedColumnName="id")
    private InsuranceCompany company;
    private String name;
    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private Employee author;
    private LocalDate date;

    private Long basicPremium;

    @OneToOne
    @JoinColumn(name="uw_policy_id", referencedColumnName="id")
    private UWPolicy uwPolicy;

    @Builder
    public Insurance(InsuranceType type, InsuranceStatus status, InsuranceCompany company, String name, Employee author, LocalDate date,UWPolicy uwPolicy,Long basicPremium) {
        this.type = type;
        this.status = status;
        this.company = company;
        this.name = name;
        this.author = author;
        this.date = date;
        this.uwPolicy=uwPolicy;
        this.basicPremium=basicPremium;
    }
}
