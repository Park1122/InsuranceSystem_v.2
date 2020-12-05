package system.insurance.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientFactorInfoDTO {
    private final Map<String, String> typeList;
    private final Map<String, String> smokeList;
    private final Map<String, String> drinkList;
    private final Map<String, String> jobList;

    @Builder
    public ClientFactorInfoDTO(Map<String, String> smokeList, Map<String, String> typeList, Map<String, String> drinkList,  Map<String, String> jobList){
        this.smokeList = smokeList;
        this.typeList = typeList;
        this.drinkList = drinkList;
        this.jobList=jobList;
    }
}
