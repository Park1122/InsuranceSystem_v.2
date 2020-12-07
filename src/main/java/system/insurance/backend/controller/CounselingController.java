package system.insurance.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.insurance.backend.dto.CounselingDTO;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.service.SalesService;

import java.util.List;

@RestController
@RequestMapping("/counseling")
public class CounselingController {
    private final SalesService salesService;

    public CounselingController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping("/record/register")
    public boolean saveRecord(@RequestParam(name = "content") String content, @RequestParam(name = "eid")int eid){
        try {
            return this.salesService.saveCounselingRecord(content, eid);
        } catch (NoEmployeeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/record/registerById")
    public boolean saveRecord(@RequestParam(name = "content") String content, @RequestParam(name = "eid")int eid, @RequestParam(name = "cid")int cid){
        try {
            return this.salesService.saveCounselingRecordById(content, eid,cid);
        } catch (NoEmployeeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/record/get")
    public List<CounselingDTO> getRecords(@RequestParam(name = "eid")int eid){
        try {
           return this.salesService.getRecordsByEmployeeId(eid);
        } catch (NoEmployeeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/record/search")
    public ResponseEntity<CounselingDTO> getRecordById(@RequestParam(name = "id")int id){
            return ResponseEntity.ok( this.salesService.getRecordByCounselingId(id));
    }


}
