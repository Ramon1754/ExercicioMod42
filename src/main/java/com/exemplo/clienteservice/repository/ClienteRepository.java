package com.exemplo.clienteservice.repository;

import com.exemplo.clienteservice.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
