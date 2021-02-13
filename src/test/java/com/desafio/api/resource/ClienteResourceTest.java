package com.desafio.api.resource;

import java.math.BigInteger;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.desafio.api.dto.ClienteDTO;
import com.desafio.model.entity.Cliente;
import com.desafio.model.enums.TipoTelefone;
import com.desafio.service.ClienteService;
import com.desafio.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest( controllers = ClienteResource.class )
@AutoConfigureMockMvc
public class ClienteResourceTest {
	
	static final String API = "/api/clientes";
	static final MediaType JSON = MediaType.APPLICATION_JSON;
	
	static final String NOME = "Ricardo";
	
	static final BigInteger CPF = new BigInteger("11111111111");
	static final String EMAIL = "teste@gmail.com";
	static final TipoTelefone TIPO_TELEFONE = TipoTelefone.CELULAR;
	static final BigInteger NUMERO_TELEFONE = new BigInteger("12111111111");
	static final BigInteger CEP = new BigInteger("12345777");
	static final String LOGRADOURO = "Teste";
	static final String BAIRRO = "Asa Norte";
	static final String CIDADE = "Brasília";
	static final String UF = "DF";
	static final String USUARIO = "admin";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	ClienteService service;
	@MockBean
	UsuarioService usuarioService;
	
	@Test
	public void criarCliente() throws Exception {
		ClienteDTO dto = ClienteDTO.builder()
				.nome(NOME)
				.cpf(CPF)
				.email(EMAIL)
				.tipo(TIPO_TELEFONE.name())
				.numeroTelefone(NUMERO_TELEFONE)
				.cep(CEP)
				.logradouro(LOGRADOURO)
				.bairro(BAIRRO)
				.cidade(CIDADE)
				.uf(UF)
				.usuario(1l)
				.build();
		
		Cliente cliente = Cliente.builder()
				.id(1l)
				.nome(NOME)
				.cpf(CPF)
				.email(EMAIL)
				.tipoTelefone(TIPO_TELEFONE)
				.numeroTelefone(NUMERO_TELEFONE)
				.cep(CEP)
				.logradouro(LOGRADOURO)
				.bairro(BAIRRO)
				.cidade(CIDADE)
				.uf(UF)
				.dataCadastro(LocalDate.now())
				.usuario(USUARIO)
				.build();
		
		Mockito.when(service.salvar(Mockito.any(Cliente.class))).thenReturn(cliente);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(cliente.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(cliente.getNome()));
		
	}
	
}
