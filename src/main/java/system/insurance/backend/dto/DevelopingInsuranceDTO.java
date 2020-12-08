package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DevelopingInsuranceDTO {
    private final int id;
    private final String name;
    private final String author;
    private final LocalDate date;

    @Builder
    public DevelopingInsuranceDTO(int id, String name, LocalDate date, String author) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.author = author;
    }
}
