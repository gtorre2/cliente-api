package com.desafio.model.entity;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.desafio.model.enums.TipoTelefone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "cliente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
    @Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "cpf", nullable = false, length = 11)
	private BigInteger cpf;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tipo_telefone")
	@Enumerated(value = EnumType.STRING)
	private TipoTelefone tipoTelefone;
	
	@Column(name = "numero_telefone")
	private BigInteger numeroTelefone;
	
	@Column(name = "cep", nullable = false)
	private BigInteger cep;
	
	@Column(name = "logradouro", nullable = true)
	private String logradouro;
	
	@Column(name = "bairro", nullable = true)
	private String bairro;
	
	@Column(name = "cidade", nullable = true)
	private String cidade;
	
	@Column(name = "uf", nullable = true)
	private String uf;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	
}
