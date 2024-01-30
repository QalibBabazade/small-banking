package com.fintech.customer.repositories;

import com.fintech.customer.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    Optional<CustomerEntity> getCustomerEntitiesByPinAndActive(String pin, Integer active);

    Optional<CustomerEntity> getCustomerEntitiesByPinAndGsmNumberAndActive(String pin, String gsmNumber, Integer active);
}
