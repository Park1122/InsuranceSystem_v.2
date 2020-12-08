package system.insurance.backend.dbo.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum Sex implements Serializable{
    MALE("남"), FEMALE("여");
    private String desc;
}
