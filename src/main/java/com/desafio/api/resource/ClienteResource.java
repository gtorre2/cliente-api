package com.desafio.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.api.dto.ClienteDTO;
import com.desafio.exception.RegraNegocioException;
import com.desafio.model.entity.Cliente;
import com.desafio.model.entity.Usuario;
import com.desafio.model.enums.TipoTelefone;
import com.desafio.service.ClienteService;
import com.desafio.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteResource {

	private final ClienteService service;
	private final UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> buscar(@RequestParam(value ="nome" , required = false) String nome) {
		Cliente clienteFiltro = new Cliente();
		clienteFiltro.setNome(nome);
		
		List<Cliente> clientes = service.buscar(clienteFiltro);
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ClienteDTO> obterCliente(@PathVariable("id") Long id) {
		Optional<Cliente> cliente = service.buscarClientePorId(id);
		
		if (cliente.isPresent()) {
			return ResponseEntity.ok(converter(cliente.get()));
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Cliente> salvar(@RequestBody ClienteDTO dto) {
		Cliente cliente = converter(dto);
		cliente = service.salvar(cliente);
		
		if(cliente != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(cliente);	
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
		Optional<Cliente> cliente = service.buscarClientePorId(id);
		
		if (!cliente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Cliente clienteSalvo = converter(dto);
		clienteSalvo.setId(cliente.get().getId());
		
		return ResponseEntity.ok(clienteSalvo);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Cliente> deletar( @PathVariable("id") Long id ) {
		Optional<Cliente> cliente = service.buscarClientePorId(id);
		
		if (!cliente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		service.deletar(cliente.get());
		
		return ResponseEntity.noContent().build();
	}
	
	private ClienteDTO converter(Cliente cliente) {
		return ClienteDTO.builder()
					.id(cliente.getId())
					.nome(cliente.getNome())
					.email(cliente.getEmail())
					.cpf(cliente.getCpf())
					.tipo(cliente.getTipoTelefone().name())
					.numeroTelefone(cliente.getNumeroTelefone())
					.cep(cliente.getCep())
					.logradouro(cliente.getLogradouro())
					.bairro(cliente.getBairro())
					.cidade(cliente.getCidade())
					.uf(cliente.getUf())
					.build();
	}
	
	private Cliente converter(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setId(dto.getId());
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setCpf(dto.getCpf());
		
		Optional<Usuario> usuario = usuarioService.obterPorId(dto.getUsuario());
		
		if(usuario.isPresent()) {
			cliente.setUsuario(usuario.get().getNome());	
		} else {
			new RegraNegocioException("Usuário não encontrado para o Id informado.");
		}
		
		if(dto.getTipo() != null) {
			cliente.setTipoTelefone(TipoTelefone.valueOf(dto.getTipo()));
		}
		
		cliente.setNumeroTelefone(dto.getNumeroTelefone());
		cliente.setCep(dto.getCep());
		cliente.setLogradouro(dto.getLogradouro());
		cliente.setBairro(dto.getBairro());
		cliente.setCidade(dto.getCidade());
		cliente.setUf(dto.getUf());
		
		return cliente;
	}
	
}
