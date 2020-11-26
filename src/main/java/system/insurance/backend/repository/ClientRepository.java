package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import system.insurance.backend.dbo.client.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
//    List<Client> findAllByType(ClientType type);
    @Query("from RegisteringClient")
    Optional<Client> findByRrn(String rrn);
}
