package com.desafio.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByNomeContainingIgnoreCase(String nome);
	
}
