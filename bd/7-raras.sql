-- =========================================================
--  Listar as figurinhas mais raras do sistema por álbum
-- =========================================================

SELECT 
    a.nome AS nome_do_album,
    f.numero AS numero_da_figurinha,
    f.descricao,
    COUNT(c.usuario_id) AS quantidade_de_usuarios_que_possuem
FROM figurinhas f
JOIN albuns a ON f.album_id = a.id
-- LEFT JOIN garante que mesmo as figurinhas que NINGUÉM tem apareçam como as mais raras (zero donos)
LEFT JOIN colecoes c ON f.id = c.figurinha_id AND c.quantidade > 0
GROUP BY a.id, a.nome, f.id, f.numero, f.descricao
ORDER BY a.nome, quantidade_de_usuarios_que_possuem ASC;