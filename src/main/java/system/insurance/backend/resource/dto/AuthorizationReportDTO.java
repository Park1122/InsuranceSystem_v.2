package system.insurance.backend.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizationReportDTO {
    private int id;
    private String path;
    private String fileName;
    private String authorName;
    private Date date;

    @Builder
    public AuthorizationReportDTO(int id, String path, String fileName, String authorName, Date date) {
        this.id = id;
        this.path = path;
        this.fileName=fileName;
        this.authorName = authorName;
        this.date = date;
    }
}
