package io.github.alberes.register.manager.resource.server.repositories;

import io.github.alberes.register.manager.resource.server.domains.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    public Page<Address> findByUserAccountId(UUID userId, PageRequest pageRequest);
}
