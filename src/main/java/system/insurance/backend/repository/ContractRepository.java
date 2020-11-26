package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.contract.Contract;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.dbo.insurance.Insurance;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findAllBySalesPerson(Employee employee);

    Optional<Contract> findByClient(Client client);

    List<Contract> findAllByClientAndSalesPerson(Client client, Employee salesPerson);

    List<Contract> findAllByInsurance(Insurance insurance);

    Collection<Object> findAllByClientAndSalesPersonAndUnderwritingPassed(Client client, Employee employee, boolean underwritingPassed);

//    List<Contract> findAll();
}
