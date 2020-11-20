package system.insurance.backend.accident;

import lombok.*;

import javax.persistence.*;
import java.awt.*;
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
    public Accident(Date date, String accidentArea, AccidentType accidentType) {
        this.date = date;
        this.accidentArea = accidentArea;
        this.accidentType = accidentType;
    }

    @Getter
	@Setter
    public static class DamageAssessmentInfo {
        private String basis;
        private long amount;
        private String paymentMethod;
    }

	@Getter
	@Setter
    public static class ResponsibilityInfo {
        private ArrayList<String> relevantRegulations;
        private File basisFile;
        private boolean responsibility;
        private String judgementBasis;
    }

    @Getter
	@Setter
    public static class AccidentInquiryInfo {
        private ArrayList<String> competentAuthority;
        private File record;
        private File picture;
        private File video;
        private String scenario;
        private long processingCost;
        private ArrayList<String> damages;

    }
}
