package system.insurance.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.InstructionDTO;
import system.insurance.backend.service.SalesService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instruction")
public class InstructionController {
    private final SalesService salesService;

    @Autowired
    public InstructionController(SalesService salesService) {
        this.salesService = salesService;
    }

    //sales instruction 저장하기.
    @PostMapping("/sales/register")
    public ResponseEntity<Boolean> registerInstruction(@RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(this.salesService.instructionRegister(body.get("title"), body.get("instruction"), Integer.parseInt(body.get("id"))));
        } catch (NoEmployeeException e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }

    //sales Instruction 목록 불러오기.
    @GetMapping("/sales/list")
    @ResponseBody
    public List<InstructionDTO> getInstructionList() {
        return this.salesService.getSalesInstructionList();
    }
}
