-- =========================================================
-- 1. TABELAS (ESTRUTURA)
-- =========================================================

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(50),
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    uf CHAR(2) NOT NULL,
    cep CHAR(8) NOT NULL,
    nota NUMERIC(3,1) DEFAULT 5.0 CHECK (nota >= 0.0 AND nota <= 5.0)
);

CREATE TABLE albuns (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ano_lancamento INT NOT NULL,
    editora VARCHAR(100) NOT NULL
);

CREATE TABLE figurinhas (
    id SERIAL PRIMARY KEY,
    album_id INT NOT NULL,
    numero INT NOT NULL,
    descricao TEXT,
    categoria VARCHAR(20) NOT NULL CHECK (categoria IN ('brilhante', 'normal')),
    FOREIGN KEY (album_id) REFERENCES albuns(id) ON DELETE CASCADE,
    UNIQUE (album_id, numero)
);

CREATE TABLE usuario_album (
    usuario_id INT NOT NULL,
    album_id INT NOT NULL,
    PRIMARY KEY (usuario_id, album_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (album_id) REFERENCES albuns(id) ON DELETE CASCADE
);

CREATE TABLE colecoes (
    usuario_id INT NOT NULL,
    figurinha_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1 CHECK (quantidade >= 0),
    PRIMARY KEY (usuario_id, figurinha_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (figurinha_id) REFERENCES figurinhas(id) ON DELETE CASCADE
);

CREATE TABLE propostas_troca (
    id SERIAL PRIMARY KEY,
    usuario_id_origem INT NOT NULL,
    usuario_id_destino INT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'Pendente' 
        CHECK (status IN ('Pendente', 'Aceita', 'Recusada', 'Enviada', 'Concluída')),
    FOREIGN KEY (usuario_id_origem) REFERENCES usuarios(id),
    FOREIGN KEY (usuario_id_destino) REFERENCES usuarios(id),
    CHECK (usuario_id_origem <> usuario_id_destino)
);

CREATE TABLE itens_proposta (
    proposta_id INT NOT NULL,
    figurinha_id INT NOT NULL,
    usuario_ofertante_id INT NOT NULL,
    PRIMARY KEY (proposta_id, figurinha_id),
    FOREIGN KEY (proposta_id) REFERENCES propostas_troca(id) ON DELETE CASCADE,
    FOREIGN KEY (figurinha_id) REFERENCES figurinhas(id),
    FOREIGN KEY (usuario_ofertante_id) REFERENCES usuarios(id)
);

-- =========================================================
-- 2. VISÕES (VIEWS LOGICAS)
-- =========================================================

CREATE VIEW v_figurinhas_repetidas AS
SELECT usuario_id, figurinha_id
FROM colecoes
WHERE quantidade > 1;

CREATE VIEW v_figurinhas_faltantes AS
SELECT ua.usuario_id, f.id AS figurinha_id, f.album_id
FROM usuario_album ua
JOIN figurinhas f ON ua.album_id = f.album_id
LEFT JOIN colecoes c ON ua.usuario_id = c.usuario_id AND f.id = c.figurinha_id
WHERE c.quantidade IS NULL OR c.quantidade = 0;