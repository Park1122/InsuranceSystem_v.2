package system.insurance.backend.dbo;

import lombok.*;
import system.insurance.backend.dbo.employee.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "market_investigation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MarketInvestigation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private LocalDate date;
    @ManyToOne
    @JoinColumn
    private Employee author;
    @Column(columnDefinition = "text")
    private String needs;
    @Column(length = 500)
    private String targetClient;

    @Builder
    public MarketInvestigation(String title, LocalDate date, Employee author, String needs, String targetClient) {
        this.title = title;
        this.date = date;
        this.author = author;
        this.needs = needs;
        this.targetClient = targetClient;
    }
}
