package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizationReportDTO {
    private int id;
    private String path;
    private String fileName;
    private String authorName;
    private LocalDate date;

    @Builder
    public AuthorizationReportDTO(int id, String path, String fileName, String authorName, LocalDate date) {
        this.id = id;
        this.path = path;
        this.fileName=fileName;
        this.authorName = authorName;
        this.date = date;
    }
}
