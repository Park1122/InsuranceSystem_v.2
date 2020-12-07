package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private final int id;
    private final String name;
    private final String auth;

    @Builder
    public EmployeeDTO(int id, String name, String auth) {
        this.id = id;
        this.name = name;
        this.auth = auth;
    }
}