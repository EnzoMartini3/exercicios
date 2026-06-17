-- =========================================================
--  Figurinhas mais repetidas do sistema por álbum
-- =========================================================
SELECT 
    a.nome AS nome_do_album,
    f.numero AS numero_da_figurinha,
    f.descricao,
    SUM(c.quantidade - 1) AS quantidade_total_de_repetidas
FROM colecoes c
JOIN figurinhas f ON c.figurinha_id = f.id
JOIN albuns a ON f.album_id = a.id
WHERE c.quantidade > 1 -- Considera apenas onde há sobra
GROUP BY a.id, a.nome, f.id, f.numero, f.descricao
ORDER BY quantidade_total_de_repetidas DESC, a.nome;