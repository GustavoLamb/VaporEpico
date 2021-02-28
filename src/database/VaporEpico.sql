DROP DATABASE IF EXISTS vaporepico;
CREATE DATABASE IF NOT EXISTS vaporepico;

USE vaporepico;

CREATE TABLE Usuarios (
    id_usuario BIGINT  AUTO_INCREMENT NOT NULL PRIMARY KEY UNIQUE,
    nome VARCHAR(255)  NOT NULL,
    data_nascimento DATE NOT NULL,
    email VARCHAR(50) NOT NULL ,
    senha VARCHAR(30)  NOT NULL,
    data_criacao DATE  NOT NULL,
    tipo ENUM('ADMINISTRADOR', 'COMUM')  NOT NULL
);

CREATE TABLE Jogos (
    id_jogo BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    imagem VARCHAR(255),
    data_lancamento DATE NOT NULL,
    usuario BIGINT 
);

CREATE TABLE Carrinhos (
    id_carrinho BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    data_criacao DATE NOT NULL,
    usuario BIGINT 
);

CREATE TABLE Carrinhos_jogos (
    jogo BIGINT,
    carrinho BIGINT
);
 
ALTER TABLE Jogos ADD CONSTRAINT FK_Jogos_2
    FOREIGN KEY (usuario)
    REFERENCES Usuarios (id_usuario)
    ON DELETE SET NULL;
 
ALTER TABLE Carrinhos ADD CONSTRAINT FK_Carrinho_2
    FOREIGN KEY (usuario)
    REFERENCES Usuarios (id_usuario)
    ON DELETE CASCADE;
 
ALTER TABLE Carrinhos_jogos ADD CONSTRAINT FK_Carrinho_jogo_1
    FOREIGN KEY (jogo)
    REFERENCES Jogos (id_jogo)
    ON DELETE RESTRICT;
 
ALTER TABLE Carrinhos_jogos ADD CONSTRAINT FK_Carrinho_jogo_2
    FOREIGN KEY (carrinho)
    REFERENCES Carrinhos (id_carrinho)
    ON DELETE SET NULL;