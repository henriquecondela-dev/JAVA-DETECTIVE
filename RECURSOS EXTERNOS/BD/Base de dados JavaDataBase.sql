-- =========================================================
-- JAVA DETECTIVE
-- EPISÓDIO 01 — O CASO MOREAU
-- BANCO DE DADOS
-- MySQL 8.x
-- =========================================================


-- =========================================================
-- CRIAR BANCO DE DADOS
-- =========================================================

CREATE DATABASE IF NOT EXISTS javaDetective;

USE javaDetective;


-- =========================================================
-- I. PESSOA
-- Classe mãe de Jogador
-- =========================================================

CREATE TABLE pessoa (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,

    idade INT,

    genero ENUM('M', 'F'),

    descricao TEXT

) ENGINE=InnoDB;


-- =========================================================
-- II. JOGADOR
-- Jogador herda Pessoa
-- =========================================================

CREATE TABLE jogador (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    pessoa_id BIGINT NOT NULL UNIQUE,

    progresso INT DEFAULT 0,

    etapa_atual INT DEFAULT 1,

    pontuacao INT DEFAULT 0,

    CONSTRAINT fk_jogador_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoa(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;


-- =========================================================
-- III. CASO
-- Representa a investigação
-- =========================================================

CREATE TABLE caso (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    titulo VARCHAR(200) NOT NULL,

    descricao TEXT,

    estado enum ('COMPLETO','NAO_COMPLETO') DEFAULT 'NAO_COMPLETO',

    vitima_id BIGINT,
    
    CONSTRAINT fk_caso_vitima
        FOREIGN KEY (vitima_id)
        REFERENCES pessoa(id)
        ON DELETE SET NULL

) ENGINE=InnoDB;


-- =========================================================
-- IV. ETAPA
-- Cada caso possui várias etapas
-- =========================================================

CREATE TABLE etapa (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    caso_id BIGINT NOT NULL,

    numero INT NOT NULL,

    titulo VARCHAR(200) NOT NULL,

    descricao TEXT,

    desbloqueada BOOLEAN DEFAULT FALSE,

    concluida BOOLEAN DEFAULT FALSE,

    minimo_evidencias INT NOT NULL DEFAULT 1,

    CONSTRAINT fk_etapa_caso
        FOREIGN KEY (caso_id)
        REFERENCES caso(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_etapa_numero
        UNIQUE (caso_id, numero)

) ENGINE=InnoDB;


-- =========================================================
-- V. OBJETIVO
-- Objetivos associados às etapas
-- =========================================================

CREATE TABLE objetivo (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    etapa_id BIGINT NOT NULL,

    descricao TEXT NOT NULL,

    minimo_relacoes_codigo INT DEFAULT 4,

    concluido BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_objetivo_etapa
        FOREIGN KEY (etapa_id)
        REFERENCES etapa(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;


-- =========================================================
-- VI. SUSPEITO
-- Relacionado a Pessoa
-- =========================================================

CREATE TABLE suspeito (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    pessoa_id BIGINT NOT NULL,

    caso_id BIGINT NOT NULL,

    motivo TEXT,

    CONSTRAINT fk_suspeito_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoa(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_suspeito_caso
        FOREIGN KEY (caso_id)
        REFERENCES caso(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_suspeito_pessoa_caso
        UNIQUE (pessoa_id, caso_id)

) ENGINE=InnoDB;


-- =========================================================
-- VII. TESTEMUNHO
-- Informação fornecida por uma pessoa
-- =========================================================

CREATE TABLE testemunho (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    testemunha_id BIGINT NOT NULL,

    descricao TEXT NOT NULL,

    data_hora DATETIME ,

    -- confiabilidade INT,

    CONSTRAINT fk_testemunho_testemunha
        FOREIGN KEY (testemunha_id)
        REFERENCES pessoa(id)
        ON DELETE CASCADE

   /* CONSTRAINT chk_confiabilidade
        CHECK (
            confiabilidade >= 0
            AND confiabilidade <= 100
        )*/

) ENGINE=InnoDB;


-- =========================================================
-- VIII. CASO_TESTEMUNHA
-- Relação entre Caso e Testemunho
-- =========================================================

CREATE TABLE caso_testemunha (
    caso_id BIGINT NOT NULL,
    testemunho_id BIGINT NOT NULL,
    PRIMARY KEY (
        caso_id,
        testemunho_id
    ),
    CONSTRAINT fk_caso_testemunha_caso
        FOREIGN KEY (caso_id)
        REFERENCES caso(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_caso_testemunha_testemunho
        FOREIGN KEY (testemunho_id)
        REFERENCES testemunho(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;


-- =========================================================
-- IX. LOCAL
-- =========================================================

CREATE TABLE localizacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
) ENGINE=InnoDB;


-- =========================================================
-- X. EVENTO
-- =========================================================

CREATE TABLE evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT NOT NULL,
    data_hora DATETIME NOT NULL,
    local_id BIGINT,
    CONSTRAINT fk_evento_local
        FOREIGN KEY (local_id)
        REFERENCES localizacao(id)
        ON DELETE SET NULL

) ENGINE=InnoDB;


-- =========================================================
-- XI. EVENTO_PESSOA
-- Relação N:N
-- Evento <-> Pessoa
-- =========================================================

CREATE TABLE evento_pessoa (
    evento_id BIGINT NOT NULL,
    pessoa_id BIGINT NOT NULL,
    PRIMARY KEY (
        evento_id,
        pessoa_id
    ),
    CONSTRAINT fk_evento_pessoa_evento
        FOREIGN KEY (evento_id)
        REFERENCES evento(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_evento_pessoa_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoa(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;

-- =========================================================
-- XII. REGISTRO_ACESSO
-- =========================================================

CREATE TABLE registroDeAcesso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT,
    data_hora DATETIME NOT NULL,
    tipo_acesso ENUM(
        'ENTRADA',
        'SAIDA'
    ) NOT NULL,
    local_id BIGINT NOT NULL,
    pessoa_id BIGINT NOT NULL,
    CONSTRAINT fk_registro_local
        FOREIGN KEY (local_id)
        REFERENCES localizacao(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_registro_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoa(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- XIII. DOCUMENTO
-- =========================================================

CREATE TABLE documento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    tipo ENUM(
        'FISICO',
        'DIGITAL'
    ) NOT NULL,
    conteudo TEXT,
    data_documento DATE
) ENGINE=InnoDB;


-- =========================================================
-- XIV. EVIDENCIA
-- =========================================================

CREATE TABLE evidencia (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    titulo VARCHAR(200) NOT NULL,

    descricao TEXT,

    documento_id BIGINT,

    importancia ENUM ('1','2','3') DEFAULT '1' ,
    coletado boolean default false,

    CONSTRAINT fk_evidencia_documento
        FOREIGN KEY (documento_id)
        REFERENCES documento(id)
        ON DELETE SET NULL
) ENGINE=InnoDB;


-- =========================================================
-- XV. ETAPA_EVIDENCIA
-- Relação Etapa -> Evidência
-- =========================================================

CREATE TABLE etapa_evidencia (
    etapa_id BIGINT NOT NULL,
    evidencia_id BIGINT NOT NULL,
    PRIMARY KEY (
        etapa_id,
        evidencia_id
    ),
    CONSTRAINT fk_etapa_evidencia_etapa
        FOREIGN KEY (etapa_id)
        REFERENCES etapa(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_etapa_evidencia_evidencia
        FOREIGN KEY (evidencia_id)
        REFERENCES evidencia(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;


-- =========================================================
-- XVI. ALIBI
-- =========================================================

CREATE TABLE alibi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    suspeito_id BIGINT NOT NULL,
    descricao TEXT NOT NULL,
    inicio DATETIME not null,
    fim DATETIME not null,
    
    CONSTRAINT fk_alibi_suspeito
        FOREIGN KEY (suspeito_id)
        REFERENCES suspeito(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_periodo_alibi
        CHECK (fim >= inicio)
) ENGINE=InnoDB;


-- =========================================================
-- XVII. MENSAGEM
-- =========================================================

CREATE TABLE mensagem (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    conteudo TEXT NOT NULL

) ENGINE=InnoDB;


-- =========================================================
-- XVIII. ETAPA_MENSAGEM
-- Relação Etapa -> Mensagem
-- =========================================================

CREATE TABLE etapa_mensagem (
    etapa_id BIGINT NOT NULL,
    mensagem_id BIGINT NOT NULL,
    PRIMARY KEY (
        etapa_id,
        mensagem_id
    ),

    CONSTRAINT fk_etapa_mensagem_etapa
        FOREIGN KEY (etapa_id)
        REFERENCES etapa(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_etapa_mensagem_mensagem
        FOREIGN KEY (mensagem_id)
        REFERENCES mensagem(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;


-- =========================================================
-- XIX. DESAFIO_PROGRAMACAO
-- =========================================================

CREATE TABLE desafioDeProgramacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    caso_id BIGINT NOT NULL,
    descricao TEXT,
    etapa_id BIGINT NOT NULL,
    classe_esperada TEXT,
    metodo_esperado TEXT,
    concluido BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_desafio_caso
        FOREIGN KEY (caso_id)
        REFERENCES caso(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_desafio_etapa
        FOREIGN KEY (etapa_id)
        REFERENCES etapa(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;


-- =========================================================
-- XX. PROGRESSO_CASO
-- Relação Jogador -> Caso
-- =========================================================

CREATE TABLE progressoCaso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    jogador_id BIGINT NOT NULL,

    caso_id BIGINT NOT NULL,
    etapa_atual_id BIGINT,
    pontuacao INT DEFAULT 0,

    CONSTRAINT fk_progresso_jogador
        FOREIGN KEY (jogador_id)
        REFERENCES jogador(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_progresso_caso
        FOREIGN KEY (caso_id)
        REFERENCES caso(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_progresso_etapa
        FOREIGN KEY (etapa_atual_id)
        REFERENCES etapa(id)
        ON DELETE SET NULL

    /*CONSTRAINT uq_jogador_caso
        UNIQUE (
            jogador_id,
            caso_id
        )*/
) ENGINE=InnoDB;


-- =========================================================
-- XXI. PROGRESSO_EVIDENCIA
-- Relação N:N
-- ProgressoCaso <-> Evidência
-- =========================================================

CREATE TABLE progresso_evidencia (

    progresso_caso_id BIGINT NOT NULL,

    evidencia_id BIGINT NOT NULL,

    coletada BOOLEAN DEFAULT false,
	
    data_coleta DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (
        progresso_caso_id,
        evidencia_id
    ),

    CONSTRAINT fk_progresso_evidencia_progresso
        FOREIGN KEY (progresso_caso_id)
        REFERENCES progressoCaso(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_progresso_evidencia_evidencia
        FOREIGN KEY (evidencia_id)
        REFERENCES evidencia(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;


-- =========================================================
-- XXII. ACUSACAO
-- =========================================================

CREATE TABLE acusacao (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    jogador_id BIGINT NOT NULL,

    caso_id BIGINT NOT NULL,

    suspeito_acusado_id BIGINT,

    resultado BOOLEAN,

    data_acusacao DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_acusacao_jogador
        FOREIGN KEY (jogador_id)
        REFERENCES jogador(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_acusacao_caso
        FOREIGN KEY (caso_id)
        REFERENCES caso(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_acusacao_suspeito
        FOREIGN KEY (suspeito_acusado_id)
        REFERENCES suspeito(id)
        ON DELETE SET NULL

) ENGINE=InnoDB;


-- =========================================================
-- XXIII. ACUSACAO_EVIDENCIA
-- Relação N:N
-- =========================================================

CREATE TABLE acusacao_evidencia (
    acusacao_id BIGINT NOT NULL,
    evidencia_id BIGINT NOT NULL,
    PRIMARY KEY (
        acusacao_id,
        evidencia_id
    ),
    CONSTRAINT fk_acusacao_evidencia_acusacao
        FOREIGN KEY (acusacao_id)
        REFERENCES acusacao(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_acusacao_evidencia_evidencia
        FOREIGN KEY (evidencia_id)
        REFERENCES evidencia(id)
        ON DELETE CASCADE

) ENGINE=InnoDB;


-- =========================================================
-- XXIV. ACUSACAO_SUSPEITO
-- Relação N:N
-- Suspeitos selecionados pelo jogador
-- =========================================================

CREATE TABLE acusacao_suspeito (
    acusacao_id BIGINT NOT NULL,
    suspeito_id BIGINT NOT NULL,
    PRIMARY KEY (
    acusacao_id,
    suspeito_id
    ),
    CONSTRAINT fk_acusacao_suspeito_acusacao
    FOREIGN KEY (acusacao_id)
    REFERENCES acusacao(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_acusacao_suspeito_suspeito
    FOREIGN KEY (suspeito_id)
    REFERENCES suspeito(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- XXIV. PROGRESSO_DESAFIO
-- Relação N:N
-- Desafios concluídos pelo jogador
-- =========================================================

CREATE TABLE progresso_desafio (
    progresso_caso_id BIGINT NOT NULL,
    desafio_id BIGINT NOT NULL,
    concluido BOOLEAN DEFAULT FALSE,
    data_conclusao DATETIME NULL,

    PRIMARY KEY (progresso_caso_id, desafio_id),

    FOREIGN KEY (progresso_caso_id)
    REFERENCES progressoCaso(id)
    ON DELETE CASCADE,

    FOREIGN KEY (desafio_id)
    REFERENCES desafioDeProgramacao(id)
    ON DELETE CASCADE,

    CONSTRAINT uq_jogador_caso
    UNIQUE (jogador_id, caso_id)

)ENGINE=InnoDB;