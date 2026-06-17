-- =========================================================
--  Listar os álbuns cadastrados com as quantidades de figurinhas
-- =========================================================
SELECT 
    a.nome AS nome_do_album,
    a.ano_lancamento,
    COUNT(f.id) AS quantidade_de_figurinhas
FROM albuns a
LEFT JOIN figurinhas f ON a.id = f.album_id
GROUP BY a.id, a.nome, a.ano_lancamento
ORDER BY a.nome;