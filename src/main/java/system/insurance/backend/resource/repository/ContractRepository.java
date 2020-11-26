package system.insurance.backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.client.Client;
import system.insurance.backend.contract.Contract;
import system.insurance.backend.employee.Employee;
import system.insurance.backend.insurance.Insurance;

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
