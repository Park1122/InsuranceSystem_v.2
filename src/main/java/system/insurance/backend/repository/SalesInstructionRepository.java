package system.insurance.backend.repository;

import system.insurance.backend.dbo.instruction.Instruction;
import system.insurance.backend.dbo.instruction.InstructionType;
import system.insurance.backend.dbo.instruction.SalesInstruction;

import java.util.List;

public interface SalesInstructionRepository extends InstructionRepository<SalesInstruction, Integer> {
    List<Instruction> findAllByType(InstructionType type);
}
