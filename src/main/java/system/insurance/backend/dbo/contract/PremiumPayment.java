package system.insurance.backend.dbo.contract;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Setter
public class PremiumPayment {

    @Id
    private int id;

    @JoinColumn(name="contract_id",referencedColumnName = "id")
    @ManyToOne
    private Contract contract;

    private LocalDate date;

    private Long paidAmount;

    @Builder
    public PremiumPayment(Contract contract, LocalDate date, Long paidAmount) {
        this.contract = contract;
        this.date = date;
        this.paidAmount = paidAmount;
    }
}
