package system.insurance.backend.dbo.instruction;

import lombok.*;
import system.insurance.backend.dbo.employee.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public abstract class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 500)
    private String title;
    @Column(columnDefinition = "text")
    private String instruction;
    @Enumerated(EnumType.STRING)
    private InstructionType type;
    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private Employee author;
    @Column(columnDefinition = "date")
    private LocalDate date;

    public Instruction(String title, String instruction, InstructionType type, Employee author, LocalDate date) {
        this.title = title;
        this.instruction = instruction;
        this.type = type;
        this.author = author;
        this.date = date;
    }
}
