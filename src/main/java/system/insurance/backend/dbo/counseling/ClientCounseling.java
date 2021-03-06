package system.insurance.backend.dbo.counseling;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.employee.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "client_counseling")
public class ClientCounseling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "counselor_id", referencedColumnName = "id")
    private Employee counselor;
    private String content;
    private LocalDate date;

    @Builder
    public ClientCounseling(Employee counselor, String content, LocalDate date,Client client) {
        this.counselor = counselor;
        this.content = content;
        this.date = date;
        this.client=client;
    }
}
