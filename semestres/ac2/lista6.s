.text

logic:
    la a0, _end
    lw t1, a0, 0
    blt, t0, zero, phase1
    beq t0, zero, phase1
    j phase2

phase1:
    mul t0, t0, t0           # x^2
    mul t0, t0, t0           # x^4
    addi t0, t0, -1
    j fim

phase2:
    mv t4, t0
    mul t0, t0, t0
    add t0, t0, t4
    addi t0, t0, +1
    j fim

fim:
    addi t1, t0, 4           # t1 = Endereço da 2ª posição livre (_end + 4)
    sw a0, t1, 0             # Salva o resultado y (em a0) na 2ª posição livre
    ret