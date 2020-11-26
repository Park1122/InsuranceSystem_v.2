package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.employee.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByName(String name);
    List<Employee> findAllByPhoneNum(String phoneNum);
    List<Employee> findAll();
    Optional<Employee> findByUidAndPassword(String uid, String password);
    Optional<Employee> findById(int id);
}
