package com.desafio.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.model.entity.Cliente;
import com.desafio.model.entity.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	boolean existsByNome(String nome);
	
	Optional<Usuario> findByNome(String nome);
	
}
