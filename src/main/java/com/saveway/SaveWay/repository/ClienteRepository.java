package com.saveway.SaveWay.repository;

import com.saveway.SaveWay.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, String> {
    Optional<Cliente> findByCpf(@Param("cpf") String cpf);
}
