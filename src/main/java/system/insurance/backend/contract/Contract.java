package system.insurance.backend.contract;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import system.insurance.backend.client.Client;
import system.insurance.backend.client.RegisteredClient;
import system.insurance.backend.client.RegisteringClient;
import system.insurance.backend.employee.Employee;
import system.insurance.backend.insurance.Insurance;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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
	@ColumnDefault("0")
	private int payment;
//	private Date dueDate;
	@ColumnDefault("false")
	private boolean compensationProvision;
	private String paymentStatus;
	@ManyToOne(targetEntity = Insurance.class)
	@JoinColumn(name="insurance_id",referencedColumnName = "id")
	private Insurance insurance;
//	private Date startDate;
	@ManyToOne(targetEntity = Employee.class)
	@JoinColumn(name="sales_person_id",referencedColumnName = "id")
	private Employee salesPerson;
	@ManyToOne(targetEntity = Insurance.class)
	@JoinColumn(name="reinsurance_id",referencedColumnName = "id")
	private Insurance reinsurance;
	@Builder
	public Contract( int payment, /*Date dueDate,*/ boolean compensationProvision,
					String paymentStatus, Insurance insurance, /*Date startDate,*/ Employee salesPerson,
					Insurance reinsurance) {

		this.payment = payment;
//		this.dueDate = dueDate;
		this.compensationProvision = compensationProvision;
		this.paymentStatus = paymentStatus;
		this.insurance = insurance;
//		this.startDate = startDate;
		this.salesPerson = salesPerson;
		this.reinsurance = reinsurance;
	}
}
