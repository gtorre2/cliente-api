CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	senha VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (nome, senha) values ('admin', '123456');
INSERT INTO usuario (nome, senha) values ('comun', '123456');