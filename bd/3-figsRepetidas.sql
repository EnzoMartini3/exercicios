-- =========================================================
--  Listar as figurinhas repetidas de um usuário em um álbum
-- =========================================================
SELECT 
    a.nome AS nome_do_album,
    f.numero AS numero_da_figurinha,
    f.descricao,
    (c.quantidade - 1) AS quantidade_disponivel_para_troca
FROM colecoes c
JOIN figurinhas f ON c.figurinha_id = f.id
JOIN albuns a ON f.album_id = a.id
WHERE c.usuario_id = 1             -- ID do Carlos
  AND f.album_id = 1               -- ID do Álbum do Brasileirão
  AND (c.quantidade - 1) > 0;      -- Garante que só mostra se houver sobra real