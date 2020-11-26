package system.insurance.backend.dbo.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("REGISTERING")
public class RegisteringClient extends Client{
    @OneToOne
    private EnvironmentalFactor environmentalFactor;
    @OneToOne
    private FinancialFactor financialFactor;
    @OneToOne
    private PhysicalFactor physicalFactor;
    @ColumnDefault("0")
    private Integer underWritingScore;
    private Boolean conformity;
    private String reason;
    private String rrn;
}
