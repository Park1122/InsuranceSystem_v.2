package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.employee.Employee;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CounselingDTO {

    private final int id;
    private final String clientName;
    private final String content;
    private final LocalDate date;





}
