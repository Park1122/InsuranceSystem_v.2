package system.insurance.backend.dbo.insurance;

import lombok.*;
import system.insurance.backend.dbo.employee.Employee;

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

    @Builder
    public Insurance(InsuranceType type, InsuranceStatus status, InsuranceCompany company, String name, Employee author, LocalDate date) {
        this.type = type;
        this.status = status;
        this.company = company;
        this.name = name;
        this.author = author;
        this.date = date;
    }
}
