package com.desafio.service;

import java.util.Optional;

import com.desafio.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String nome, String senha);
	
	Optional<Usuario> obterPorId(Long id);
	
}
