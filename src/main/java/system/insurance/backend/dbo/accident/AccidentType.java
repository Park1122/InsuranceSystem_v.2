package system.insurance.backend.dbo.accident;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AccidentType {
    INJURY("상해"), C2C("차사고:차 대 차"), C2P("차사고:차 대 사람"), FIRE("화재");

    public String desc;
    public String getDesc() {
        return this.desc;
    }
}
