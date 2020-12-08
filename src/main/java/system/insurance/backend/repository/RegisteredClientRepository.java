package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.client.Client;
import system.insurance.backend.dbo.client.RegisteredClient;

import java.util.Optional;

public interface RegisteredClientRepository extends JpaRepository<RegisteredClient, Integer> {
    Optional<Client> findByRrn(String rrn);
    Optional<Client> findByName(String name);
    Optional<Client> findByContact(String contact);

    Optional<Client> findByNameAndRrn(String name, String rrn);
}
