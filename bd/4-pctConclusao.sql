-- =========================================================
--  Percentual de conclusão de um álbum por usuário
-- =========================================================
SELECT 
    u.nome AS nome_do_usuario,
    a.nome AS nome_do_album,
    COUNT(DISTINCT c.figurinha_id) AS quantidade_que_possui,
    COUNT(DISTINCT f.id) AS quantidade_total_figurinhas,
    -- Calcula o percentual e formata com duas casas decimais
    TO_CHAR(
        (COUNT(DISTINCT c.figurinha_id)::NUMERIC / COUNT(DISTINCT f.id) * 100), 
        '990.99"%"'
    ) AS percentual_concluido
FROM usuario_album ua
JOIN usuarios u ON ua.usuario_id = u.id
JOIN albuns a ON ua.album_id = a.id
-- Traz as figurinhas existentes no álbum
JOIN figurinhas f ON a.id = f.album_id
-- Vincula com a coleção do usuário (se ele possuir a figurinha)
LEFT JOIN colecoes c ON u.id = c.usuario_id AND f.id = c.figurinha_id AND c.quantidade > 0
GROUP BY u.id, u.nome, a.id, a.nome
ORDER BY u.nome, a.nome;