package system.insurance.backend.service;

import system.insurance.backend.dbo.employee.Authority;
import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.UserDTO;

public interface UserCertificationService {
    UserDTO login(String uid, String password) throws NoEmployeeException;
    boolean authCheck(int id, Authority authority) throws NoEmployeeException;
}
