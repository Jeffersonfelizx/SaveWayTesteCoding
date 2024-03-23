package com.saveway.SaveWay.repository;

import com.saveway.SaveWay.model.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresaRepository extends CrudRepository<Empresa, String>{
    Optional<Empresa> findByCnpj(@Param("cnpj") String cnpj);
}
