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
public class AnnouncementDTO {
    private int id;
    private String title;
    private String content;
    private String authorName;
    private LocalDate date;
    private boolean priority;

    @Builder
    public AnnouncementDTO(int id, String title, String content, String authorName, LocalDate date, boolean priority) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.date = date;
        this.priority = priority;
    }
}
