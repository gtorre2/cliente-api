package com.desafio.api.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

	private Long id;
	private String nome;
	private String email;
	private BigInteger cpf;
	private String tipo;
	private BigInteger numeroTelefone;
	private BigInteger cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
	private Long usuario;
	
}
