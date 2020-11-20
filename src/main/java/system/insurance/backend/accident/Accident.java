package system.insurance.backend.accident;

import lombok.*;
import system.insurance.backend.contract.Contract;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "client_Accident")
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(targetEntity = Contract.class)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private int contractId;
    private Date date;
    private String accidentArea;
    private AccidentType accidentType;
    private boolean complete;
    @OneToOne
    private AccidentInquiryInfo inquiryInfo;
    @OneToOne
    private DamageAssessmentInfo damageAssessmentInfo;
    @OneToOne
    private ResponsibilityInfo responsibilityInfo;

    @Builder
    public Accident(Date date, String accidentArea, AccidentType accidentType, int contractId) {
        this.date = date;
        this.accidentArea = accidentArea;
        this.accidentType = accidentType;
        this.contractId = contractId;
    }

    @Entity
    @Getter
	@Setter
    public static class DamageAssessmentInfo {
        @Id
        private int id;
        private String basis;
        private long amount;
        private String paymentMethod;
    }

    @Entity
	@Getter
	@Setter
    public static class ResponsibilityInfo {
        @Id
        private int id;
        private String relevantRegulations;
        private File basisFile;
        private boolean responsibility;
        private String judgementBasis;
    }

    @Entity
    @Getter
	@Setter
    public static class AccidentInquiryInfo {
        @Id
        private int id;
        private String competentAuthority;
        private File record;
        private File picture;
        private File video;
        private String scenario;
        private long processingCost;
        private String damages;

    }
}
