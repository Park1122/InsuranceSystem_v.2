package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import system.insurance.backend.dbo.client.Sex;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private final int id;
    private final String contact;
    private final int age;
    private final String sex;
    private final String name;
    private final String email;
    private final String gift;

    private final String insuranceName;
    private final Integer underWritingScore;
}
