package system.insurance.backend.service;

import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.dto.ClientDTO;

import java.util.Map;

public interface ClientService {

    Map<Integer, ClientDTO> findAllRegisteringClient();

    ClientDTO getRegisteringClientDetail(int cid) throws NoClientException;

    boolean setConformity(int cid, boolean conformity, String reason) throws NoClientException;

    ClientDTO searchRegisteredByName(String name);
    ClientDTO searchRegisteredByContact(String contact);
    ClientDTO searchRegisteredByRRN(String rrn);
}
