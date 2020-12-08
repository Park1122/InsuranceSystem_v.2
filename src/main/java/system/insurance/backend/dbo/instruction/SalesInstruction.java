package system.insurance.backend.dbo.instruction;

import lombok.Builder;
import system.insurance.backend.dbo.employee.Employee;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "sales_instruction")
public class SalesInstruction extends Instruction{
    protected SalesInstruction() {
    }

    @Builder
    public SalesInstruction(String title, String instruction, InstructionType type, Employee author, LocalDate date) {
        super(title, instruction, type, author, date);
    }
}
