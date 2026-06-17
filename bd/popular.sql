-- =========================================================
-- 1. POPULANDO A TABELA DE USUÁRIOS
-- =========================================================
INSERT INTO usuarios (nome, email, logradouro, numero, complemento, bairro, cidade, uf, cep, nota) VALUES
('Carlos Silva', 'carlos@email.com', 'Rua das Flores', '123', 'Apto 101', 'Centro', 'Belo Horizonte', 'MG', '30100000', 5.0),
('Mariana Souza', 'mariana@email.com', 'Av. Brasil', '456', NULL, 'Alípio de Melo', 'Belo Horizonte', 'MG', '30800000', 4.8),
('Ricardo Santos', 'ricardo@email.com', 'Rua Bahia', '789', 'Bloco B', 'Savassi', 'Belo Horizonte', 'MG', '30140010', 4.2),
('Ana Costa', 'ana@email.com', 'Alameda dos Anjos', '12', NULL, 'Jardins', 'São Paulo', 'SP', '01415000', 5.0),
('Bruno Oliveira', 'bruno@email.com', 'Rua do Ouvidor', '99', 'Sala 4', 'Centro', 'Rio de Janeiro', 'RJ', '20040000', 3.5);

-- =========================================================
-- 2. POPULANDO A TABELA DE ÁLBUNS
-- =========================================================
INSERT INTO albuns (nome, ano_lancamento, editora) VALUES
('Campeonato Brasileiro 2026', 2026, 'Editora Panini'),
('Mundo dos Heróis', 2025, 'Editora Abril');

-- =========================================================
-- 3. POPULANDO A TABELA DE FIGURINHAS
-- =========================================================
-- Figurinhas do Álbum 1 (Campeonato Brasileiro 2026)
INSERT INTO figurinhas (album_id, numero, descricao, categoria) VALUES
(1, 1, 'Escudo do Atlético-MG', 'brilhante'),
(1, 2, 'Escudo do Cruzeiro', 'brilhante'),
(1, 3, 'Jogador Neymar', 'normal'),
(1, 4, 'Jogador Gabriel Barbosa', 'normal'),
(1, 5, 'Estádio Maracanã', 'brilhante');

-- Figurinhas do Álbum 2 (Mundo dos Heróis)
INSERT INTO figurinhas (album_id, numero, descricao, categoria) VALUES
(2, 1, 'Homem de Ferro', 'brilhante'),
(2, 2, 'Batman', 'normal'),
(2, 3, 'Mulher Maravilha', 'normal'),
(2, 4, 'Homem-Aranha', 'brilhante');

-- =========================================================
-- 4. USUÁRIOS ACOMPANHANDO ÁLBUNS
-- =========================================================
INSERT INTO usuario_album (usuario_id, album_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(1, 2);

-- =========================================================
-- 5. POPULANDO AS COLEÇÕES
-- =========================================================
INSERT INTO colecoes (usuario_id, figurinha_id, quantidade) VALUES
(1, 1, 2), 
(1, 3, 1),
(1, 6, 3), 
(2, 2, 3), 
(2, 3, 1), 
(2, 4, 1), 
(3, 4, 2), 
(3, 5, 1), 
(4, 7, 2), 
(4, 8, 1), 
(5, 6, 1), 
(5, 9, 2); 

-- =========================================================
-- 6. REGISTRANDO PROPOSTAS DE TROCA
-- =========================================================
INSERT INTO propostas_troca (usuario_id_origem, usuario_id_destino, status) VALUES
(1, 2, 'Pendente'),
(4, 5, 'Concluída');

-- =========================================================
-- 7. ITENS DAS PROPOSTAS DE TROCA
-- =========================================================
INSERT INTO itens_proposta (proposta_id, figurinha_id, usuario_ofertante_id) VALUES
(1, 1, 1),
(1, 2, 2),
(2, 7, 4),
(2, 9, 5);