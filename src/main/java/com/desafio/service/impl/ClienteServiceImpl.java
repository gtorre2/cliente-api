package com.desafio.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.exception.RegraNegocioException;
import com.desafio.model.entity.Cliente;
import com.desafio.model.repository.ClienteRepository;
import com.desafio.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private ClienteRepository repository;
	
	public ClienteServiceImpl(ClienteRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) {
		cliente.setDataCadastro(LocalDate.now());
		validar(cliente);
		return repository.save(cliente);
	}

	@Override
	@Transactional
	public Cliente atualizar(Cliente cliente) {
		Objects.requireNonNull(cliente.getId());
		return repository.save(cliente);
	}

	@Override
	@Transactional
	public void deletar(Cliente cliente) {
		Objects.requireNonNull(cliente.getId());
		repository.delete(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscar(Cliente clienteFiltro) {
		Example<Cliente> example = Example.of( clienteFiltro, 
				ExampleMatcher.matching()
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

	@Override
	public void validar(Cliente cliente) {
		boolean existe = repository.existsByNome(cliente.getNome());
		if(existe) {
			throw new RegraNegocioException("Já existe um cliente cadastrado com este nome.");
		}
	}

	@Override
	public Optional<Cliente> buscarClientePorId(Long id) {
		return repository.findById(id);
	}

}
