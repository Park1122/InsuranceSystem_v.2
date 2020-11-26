package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.instruction.Instruction;

import java.io.Serializable;

public interface InstructionRepository<T extends Instruction, E extends Serializable> extends JpaRepository<T, E> {
}
