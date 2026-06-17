-- =========================================================
--  Listar as oportunidades de troca de um usuário específico
-- =========================================================
SELECT 
    'Carlos Silva' AS usuario_foco,
    u_parceiro.nome AS usuario_parceiro,
    COUNT(DISTINCT repA.figurinha_id) AS qtd_figurinhas_para_oferecer,
    COUNT(DISTINCT repB.figurinha_id) AS qtd_figurinhas_para_receber
FROM v_figurinhas_repetidas repA
JOIN v_figurinhas_faltantes falB ON repA.figurinha_id = falB.figurinha_id
JOIN v_figurinhas_repetidas repB ON falB.usuario_id = repB.usuario_id
JOIN v_figurinhas_faltantes falA ON repB.figurinha_id = falA.figurinha_id AND repA.usuario_id = falA.usuario_id
JOIN usuarios u_parceiro ON falB.usuario_id = u_parceiro.id
WHERE repA.usuario_id = 1
GROUP BY u_parceiro.id, u_parceiro.nome;