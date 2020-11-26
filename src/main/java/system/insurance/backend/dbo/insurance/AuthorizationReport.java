package system.insurance.backend.dbo.insurance;

import lombok.*;
import system.insurance.backend.dbo.employee.Employee;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "authorization_report")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String path;
    private String fileName;
    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private Employee author;
    private Date date;

    @Builder
    public AuthorizationReport(String path, String fileName, Employee author, Date date) {
        this.path = path;
        this.fileName=fileName;
        this.author = author;
        this.date = date;
    }
}
