package com.desafio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.exception.RegraNegocioException;
import com.desafio.model.entity.Cliente;
import com.desafio.model.entity.Usuario;
import com.desafio.model.repository.ClienteRepository;
import com.desafio.model.repository.ClienteRepositoryTest;
import com.desafio.model.repository.UsuarioRepositoryTest;
import com.desafio.service.impl.ClienteServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClienteServiceTest {

	@SpyBean
	ClienteServiceImpl service;
	@MockBean
	ClienteRepository repository;
	
	@Test
	public void salvarUmCliente() {
		Cliente clienteParaSalvar = ClienteRepositoryTest.criarCliente();
		doNothing().when(service).validar(clienteParaSalvar);
		
		Cliente clienteSalvo = ClienteRepositoryTest.criarCliente();
		clienteSalvo.setId(1l);
		when(repository.save(clienteParaSalvar)).thenReturn(clienteSalvo);
		
		Cliente cliente = service.salvar(clienteParaSalvar);
		
		assertThat(cliente.getId()).isEqualTo(clienteSalvo.getId());
	}

	@Test
	public void clienteErroValidacaoAoSalvar() {
		Usuario usuarioSalvar = UsuarioRepositoryTest.criarUsuario();
		usuarioSalvar.setId(1l);
		Cliente clienteSalvar = ClienteRepositoryTest.criarCliente();
		clienteSalvar.setUsuario(usuarioSalvar.getNome());
		doThrow(RegraNegocioException.class).when(service).validar(clienteSalvar);
		
		catchThrowableOfType(() -> service.salvar(clienteSalvar), RegraNegocioException.class);
		verify(repository, never()).save(clienteSalvar);
	}
	
	@Test
	public void atualizarCliente() {
		//Usuario usuarioSalvar = UsuarioRepositoryTest.criarUsuario();
		Cliente clienteSalvo = ClienteRepositoryTest.criarCliente();
		clienteSalvo.setId(1l);
		clienteSalvo.setNome("Ricardo");
		
		doNothing().when(service).validar(clienteSalvo);
		
		when(repository.save(clienteSalvo)).thenReturn(clienteSalvo);
		
		service.atualizar(clienteSalvo);
		
		verify(repository, times(1)).save(clienteSalvo);
		
	}
	
	@Test
	public void erroAoAtualizarUmClienteNaoSalvo() {
		Cliente cliente = ClienteRepositoryTest.criarCliente();
		
		catchThrowableOfType( () -> service.atualizar(cliente), NullPointerException.class );
		verify(repository, never()).save(cliente);
	}
	
	@Test
	public void removerCliente() {
		Cliente cliente = ClienteRepositoryTest.criarCliente();
		cliente.setId(1l);
		
		service.deletar(cliente);
		
		verify(repository).delete(cliente);
	}
	
	@Test
	public void erroDeletarClienteNaoSalvo() {
		Cliente cliente = ClienteRepositoryTest.criarCliente();
		
		catchThrowableOfType(() -> service.deletar(cliente), NullPointerException.class);
		
		verify(repository, never()).delete(cliente);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void fitlrarClientes() {
		Cliente cliente = ClienteRepositoryTest.criarCliente();
		cliente.setId(1l);
		
		List<Cliente> lista = Arrays.asList(cliente);
		when(repository.findAll(any(Example.class))).thenReturn(lista);
		
		List<Cliente> resultado = service.buscar(cliente);
		
		assertThat(resultado).isNotEmpty().hasSize(1).contains(cliente);
		
	}
	
	@Test
	public void clientePorId() {
		Long id = 1l;
		
		Cliente cliente = ClienteRepositoryTest.criarCliente();
		cliente.setId(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(cliente));
		
		Optional<Cliente> resultado =  service.buscarClientePorId(id);
		
		assertThat(resultado.isPresent()).isTrue();
	}
	
	@Test
	public void retornarVazioQuandoClienteNaoExiste() {
		Long id = 1l;
		
		Cliente cliente = ClienteRepositoryTest.criarCliente();
		cliente.setId(id);
		
		when( repository.findById(id)).thenReturn( Optional.empty());
		
		Optional<Cliente> resultado =  service.buscarClientePorId(id);
		
		assertThat(resultado.isPresent()).isFalse();
	}
	
	@Test
	public void erroNomeUsuarioNaoExistente() {
		Mockito.when(repository.existsByNome(Mockito.anyString())).thenReturn(true);
		
		Cliente cliente = ClienteRepositoryTest.criarCliente();
		
		org.junit.jupiter.api.Assertions
			.assertThrows(RegraNegocioException.class, () -> service.validar(cliente));
	}
	
}
