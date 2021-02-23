DROP DATABASE IF EXISTS vaporepico;
CREATE DATABASE IF NOT EXISTS vaporepico;

USE vaporepico;

CREATE TABLE Usuarios (
    id_usuario BIGINT  AUTO_INCREMENT NOT NULL PRIMARY KEY UNIQUE,
    nome VARCHAR(255)  NOT NULL,
    data_nascimento DATE ,
    email VARCHAR(50) ,
    senha VARCHAR(30)  NOT NULL,
    data_criacao DATE  NOT NULL,
    tipo ENUM('ADIMINISTRADOR', 'COMUM')  NOT NULL
);

CREATE TABLE Jogos (
    id_jogo BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    imagem BLOB,
    data_lancamento DATE NOT NULL,
    usuario BIGINT 
);

CREATE TABLE Carrinho (
    id_carrinho BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    data_criacao DATE NOT NULL,
    usuario BIGINT 
);

CREATE TABLE Carrinho_jogo (
    id_carrinho_jogo BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    jogo BIGINT,
    carrinho BIGINT
);
 
ALTER TABLE Jogos ADD CONSTRAINT FK_Jogos_2
    FOREIGN KEY (usuario)
    REFERENCES Usuarios (id_usuario)
    ON DELETE SET NULL;
 
ALTER TABLE Carrinho ADD CONSTRAINT FK_Carrinho_2
    FOREIGN KEY (usuario)
    REFERENCES Usuarios (id_usuario)
    ON DELETE CASCADE;
 
ALTER TABLE Carrinho_jogo ADD CONSTRAINT FK_Carrinho_jogo_1
    FOREIGN KEY (jogo)
    REFERENCES Jogos (id_jogo)
    ON DELETE RESTRICT;
 
ALTER TABLE Carrinho_jogo ADD CONSTRAINT FK_Carrinho_jogo_2
    FOREIGN KEY (carrinho)
    REFERENCES Carrinho (id_carrinho)
    ON DELETE SET NULL;