package com.desafio.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.model.entity.Cliente;
import com.desafio.model.enums.TipoTelefone;
import com.desafio.model.repository.ClienteRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ClienteRepositoryTest {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void salvarClient() {
		Cliente cliente = criarCliente();
		
		cliente = repository.save(cliente);
		
		assertThat(cliente.getId()).isNotNull();
	}

	@Test
	public void removerCliente() {
		Cliente cliente = criarEPersistirCliente();
		
		cliente = entityManager.find(Cliente.class, cliente.getId());
		
		repository.delete(cliente);
		
		Cliente clienteInexistente = entityManager.find(Cliente.class, cliente.getId());
		assertThat(clienteInexistente).isNull();
	}

	
	@Test
	public void atualizarCliente() {
		Cliente cliente = criarEPersistirCliente();
		
		cliente.setNome("Ricardo");
		
		cliente.setCpf(new BigInteger("1234567812"));
		cliente.setEmail("teste@gmail.com");
		cliente.setTipoTelefone(TipoTelefone.COMERCIAL);
		cliente.setNumeroTelefone(new BigInteger("123456781"));
		cliente.setCep(new BigInteger("1233330000"));
		cliente.setLogradouro("Teste");
		cliente.setBairro("Teste");
		cliente.setCidade("Salvador");
		cliente.setUf("BA");
		
		repository.save(cliente);
		
		Cliente clienteAtualizado = entityManager.find(Cliente.class, cliente.getId());
		
		assertThat(clienteAtualizado.getNome()).isEqualTo("Ricardo");
		assertThat(clienteAtualizado.getCpf()).isEqualTo("1234567812");
		assertThat(clienteAtualizado.getEmail()).isEqualTo("teste@gmail.com");
		assertThat(clienteAtualizado.getTipoTelefone()).isEqualTo(TipoTelefone.COMERCIAL);
		assertThat(clienteAtualizado.getNumeroTelefone()).isEqualTo("123456781");
		assertThat(clienteAtualizado.getCep()).isEqualTo("1233330000");
		assertThat(clienteAtualizado.getLogradouro()).isEqualTo("Teste");
		assertThat(clienteAtualizado.getBairro()).isEqualTo("Teste");
		assertThat(clienteAtualizado.getCidade()).isEqualTo("Salvador");
		assertThat(clienteAtualizado.getUf()).isEqualTo("BA");
	}
	
	@Test
	public void clientePorId() {
		Cliente cliente = criarEPersistirCliente();
		
		Optional<Cliente> clienteExistente = repository.findById(cliente.getId());
		
		assertThat(clienteExistente.isPresent()).isTrue();
	}

	private Cliente criarEPersistirCliente() {
		Cliente cliente = criarCliente();
		entityManager.persist(cliente);
		return cliente;
	}
	
	public static Cliente criarCliente() {
		return Cliente.builder()
				.nome("Ricardo")
				.cpf(new BigInteger("9999999991"))
				.email("alguem@gmail.com")
				.tipoTelefone(TipoTelefone.COMERCIAL)
				.numeroTelefone(new BigInteger("611111111111"))
				.cep(new BigInteger("71231222"))
				.logradouro("Rua Tete")
				.bairro("Sul")
				.cidade("Brasília")
				.uf("DF")
				.dataCadastro(LocalDate.now())
				.usuario("admin")
				.build();
	}	
	
}
