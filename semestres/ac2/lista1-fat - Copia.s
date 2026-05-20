# FATORIAL
.text

main:
    li t0, 5 #numero a ser fatorado
    mv t1, t0 #contador

loop:
    beq zero, t1, fim
    addi t1, t1, -1
    mul t0, t0, t1
    j loop

fim:
    mv a1, t0
    li a0, 1
    ecall
    