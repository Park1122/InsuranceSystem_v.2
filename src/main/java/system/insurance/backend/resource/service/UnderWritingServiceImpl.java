package system.insurance.backend.resource.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import system.insurance.backend.insurance.Insurance;
import system.insurance.backend.resource.dto.UWPolicyDTO;
import system.insurance.backend.resource.repository.InsuranceRepository;
import system.insurance.backend.resource.repository.UnderWritingPolicyRepository;
import system.insurance.backend.underWriting.UWPolicy;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnderWritingServiceImpl implements UnderWritingService {

    private InsuranceRepository insuranceRepository;
    private UnderWritingPolicyRepository underWritingPolicyRepository;


    public UnderWritingServiceImpl(InsuranceRepository insuranceRepository, UnderWritingPolicyRepository underWritingPolicyRepository) {
        this.insuranceRepository = insuranceRepository;
        this.underWritingPolicyRepository = underWritingPolicyRepository;
    }

    @Override
    public List<UWPolicyDTO> getUnderWritingPolicyList() {
        //UWPolicy는 무조건 보험마다 하나씩 있어야 하는데, 만약 없으면 새로 만들어준다.
        List<Insurance> insuranceList = this.insuranceRepository.findAll();
        for (Insurance insurance : insuranceList) {
            Optional<UWPolicy> optional = underWritingPolicyRepository.findByInsurance(insurance);
            //존재하는 보험에 uWpolicy가 없는 경우!
            if (!optional.isPresent()) {
                //여기서 만들어준다. 하나.
                this.underWritingPolicyRepository.save(
                        UWPolicy.builder()
                                .insurance(insurance)
                                .environmentalPolicy("미정")
                                .physicalPolicy("미정")
                                .financialPolicy("미정")
                                .date(Date.valueOf(LocalDate.now()))
                                .build()
                );

            }
        }
        /////////////////////////////////////

        List<UWPolicyDTO> uwPolicyDTOList = new ArrayList<>();
        List<UWPolicy> uwPolicyList = this.underWritingPolicyRepository.findAll();
        uwPolicyList.forEach((uwPolicy) -> {
            uwPolicyDTOList.add(
             UWPolicyDTO.builder()
                     .id(uwPolicy.getInsurance().getId())
                     .name(uwPolicy.getInsurance().getName())
                     .date(uwPolicy.getDate())
                     .environmentalPolicy(uwPolicy.getEnvironmentalPolicy())
                     .physicalPolicy(uwPolicy.getPhysicalPolicy())
                     .financialPolicy(uwPolicy.getFinancialPolicy())
                     .build()
            );
        });
        return uwPolicyDTOList;
    }


}
