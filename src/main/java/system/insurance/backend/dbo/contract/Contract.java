package system.insurance.backend.dbo.contract;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.dbo.insurance.Insurance;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "contract")
public class Contract implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(targetEntity=Client.class)
	@JoinColumn(name="client_id",referencedColumnName = "id")
	private Client client;

	private boolean underwritingPassed;

	@ColumnDefault("0")
	private int payment;
	private LocalDate dueDate;
	@ColumnDefault("false")
	private boolean compensationProvision;
	private int paid;
	@ManyToOne(targetEntity = Insurance.class)
	@JoinColumn(name="insurance_id",referencedColumnName = "id")
	private Insurance insurance;
	private LocalDate startDate;
	@ManyToOne(targetEntity = Employee.class)
	@JoinColumn(name="sales_person_id",referencedColumnName = "id")
	private Employee salesPerson;
	@ManyToOne(targetEntity = Insurance.class)
	@JoinColumn(name="reinsurance_id",referencedColumnName = "id")
	private Insurance reinsurance;
	@Builder
	public Contract( boolean underwritingPassed, int payment, LocalDate dueDate, boolean compensationProvision,
					int paid, Insurance insurance, LocalDate startDate, Employee salesPerson,
					Insurance reinsurance) {
		this.underwritingPassed=underwritingPassed;
		this.payment = payment;
		this.dueDate = dueDate;
		this.compensationProvision = compensationProvision;
		this.paid = paid;
		this.insurance = insurance;
		this.startDate = startDate;
		this.salesPerson = salesPerson;
		this.reinsurance = reinsurance;
	}
}
