package system.insurance.backend.dbo.contract;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    private Date date;

    private Long paidAmount;

    @Builder
    public PremiumPayment(Contract contract, Date date, Long paidAmount) {
        this.contract = contract;
        this.date = date;
        this.paidAmount = paidAmount;
    }
}
