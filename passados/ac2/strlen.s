strlen:
    addi sp, sp, -4
    sw s0, sp, 0
    addi s0, zero, 0

strlen_loop:
    lbu t0, a0, 0
    beq t0, zero, strlen_end
    addi s0, s0, 1
    addi a0, a0, 1
    j strlen_loop

strlen_end:
    mv a0, s0
    lw s0, s0, 0
    addi sp, sp, 4
    ret