-- =========================================================
--  Listar as figurinhas faltantes de um usuário em um álbum
-- =========================================================
SELECT 
    a.nome AS nome_do_album,
    f.numero AS numero_da_figurinha,
    f.descricao
FROM v_figurinhas_faltantes vf
JOIN figurinhas f ON vf.figurinha_id = f.id
JOIN albuns a ON f.album_id = a.id
WHERE vf.usuario_id = 1  -- ID do Carlos
  AND f.album_id = 1;    -- ID do Álbum do Brasileirão