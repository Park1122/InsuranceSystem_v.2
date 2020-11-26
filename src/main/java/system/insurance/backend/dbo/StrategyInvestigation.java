package system.insurance.backend.dbo;

import lombok.*;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.dbo.insurance.Insurance;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "strategy_investigation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class StrategyInvestigation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn
    private Insurance insurance;
    private String title;
    private Date date;
    @ManyToOne
    @JoinColumn
    private Employee author;

    @Builder
    public StrategyInvestigation(Insurance insurance, String title, Date date, Employee author) {
        this.insurance = insurance;
        this.title = title;
        this.date = date;
        this.author = author;
    }
}
