package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StrategyInvestigationDTO {
    private final int insuranceId;
    private final String insurance;
    private final String company;
    private final String title;
    private final LocalDate date;
    private final int authorId;
    private final String author;

    @Builder
    public StrategyInvestigationDTO(int insuranceId, String insurance, String company, String title, LocalDate date, int authorId, String author) {
        this.insuranceId = insuranceId;
        this.insurance = insurance;
        this.company = company;
        this.title = title;
        this.date = date;
        this.authorId = authorId;
        this.author = author;
    }
}
