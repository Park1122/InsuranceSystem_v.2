package system.insurance.backend.service;

import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dto.ClientFactorInfoDTO;
import system.insurance.backend.exception.NoClientException;
import system.insurance.backend.dto.ClientDTO;

import java.util.List;
import java.util.Map;

public interface ClientService {

    Map<Integer, ClientDTO> findAllRegisteringClient();

    ClientDTO searchClientByIdAndName(int id, String name);

    ClientDTO getRegisteringClientDetail(int cid) throws NoClientException;

    boolean setConformity(int cid, boolean conformity, String reason) throws NoClientException;

    ClientDTO searchRegisteredByName(String name);
    ClientDTO searchRegisteredByContact(String contact);
    ClientDTO searchRegisteredByRRN(String rrn);

    List<ClientDTO> findAllUnregisteredClint();

    ClientDTO findUnregisteredClientByID(int cid);
    List<Client> getOnProgressContractAndLessFactorCustomers(int id);
    ClientFactorInfoDTO getClientFactorInfos(int eid);

    void saveClientFactors(int cid, String physicalSmokeFrequency, String physicalDrinkingFrequency, String environmentalDangerousArea, String environmentalDangerousHobby, String environmentalJob, long financialIncome, int financialCreditRating, long financialProperty) throws NoClientException;

    Map<String, String> getSexList();

    boolean saveNewUnRegisteredClient(String customerName, String contact, int age, String email, String sex);

    ClientDTO searchRegisteredByNameAndRrn(String rrn, String name);
}
