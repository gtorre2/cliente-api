package com.desafio.service;

import java.util.List;
import java.util.Optional;

import com.desafio.model.entity.Cliente;

public interface ClienteService {

	Cliente salvar(Cliente cliente);
	
	Cliente atualizar(Cliente cliente);
	
	void deletar(Cliente cliente);
	
	List<Cliente> buscar(Cliente clienteFiltro);
	
	void validar(Cliente cliente);
	
	Optional<Cliente> buscarClientePorId(Long id);
	
	
}
