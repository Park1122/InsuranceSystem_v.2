package system.insurance.backend.dbo.contract;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UnderWritingStatus {
    ONPROGRESS("심사중"),PASSED("통과"),REJECTED("거부");
    private final String description;
}
