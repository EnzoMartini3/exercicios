-- =========================================================
--  Usuários que possuem um álbum completo
-- =========================================================
SELECT 
    u.nome AS nome_do_usuario,
    a.nome AS nome_do_album
FROM usuario_album ua
JOIN usuarios u ON ua.usuario_id = u.id
JOIN albuns a ON ua.album_id = a.id
JOIN figurinhas f ON a.id = f.album_id
LEFT JOIN colecoes c ON u.id = c.usuario_id AND f.id = c.figurinha_id AND c.quantidade > 0
GROUP BY u.id, u.nome, a.id, a.nome
-- Filtro do agrupamento: só traz se o estoque único bater com o total do álbum
HAVING COUNT(DISTINCT c.figurinha_id) = COUNT(DISTINCT f.id)
ORDER BY u.nome, a.nome;