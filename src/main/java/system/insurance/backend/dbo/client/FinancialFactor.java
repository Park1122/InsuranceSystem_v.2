package system.insurance.backend.dbo.client;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinancialFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long income;
    private long property;
    private int creditRating;

    @Builder
    public FinancialFactor(long income, long property, int creditRating) {
        this.income = income;
        this.property = property;
        this.creditRating = creditRating;
    }
}
