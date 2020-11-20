package system.insurance.backend.accident;

import lombok.*;
import system.insurance.backend.contract.Contract;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "client_accident")
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

    @Getter
	@Setter
    @Entity
    @Table(name = "damage_assessment_info")
    public static class DamageAssessmentInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String basis;
        private Long amount;
        private String paymentMethod;
    }

	@Getter
	@Setter
    @Entity
    @Table(name = "responsibility_info")
    public static class ResponsibilityInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String relevantRegulations;
        private String basisFile;
        private boolean responsibility;
        private String judgementBasis;
    }

    @Getter
	@Setter
    @Entity
    @Table(name = "accident_inquiry_info")
    public static class AccidentInquiryInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String competentAuthority;
        private String record;
        private String picture;
        private String video;
        private String scenario;
        private Long processingCost;
        private String damages;

    }
}
