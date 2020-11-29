package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.client.ClientType;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {



    @Query("from RegisteringClient")
    List<Client> findAllByType(ClientType type);


    @Query("from RegisteringClient")
    Optional<Client> findByRrn(String rrn);
}
