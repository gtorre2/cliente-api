package com.desafio.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.desafio.exception.ErroAutenticacao;
import com.desafio.model.entity.Usuario;
import com.desafio.model.repository.UsuarioRepository;
import com.desafio.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String nome, String senha) {
		Optional<Usuario> usuario = repository.findByNomeContainingIgnoreCase(nome);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o nome informado.");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}

		return usuario.get();
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

}
