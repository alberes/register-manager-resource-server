package io.github.alberes.register.manager.resource.server.repositories;

import io.github.alberes.register.manager.resource.server.domains.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    public Client findByClientId(String clientId);

    public void deleteByClientId(String clientId);

    public Page<Client> findById(UUID id, PageRequest pageRequest);
}
