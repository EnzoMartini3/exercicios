# FIBONACCIO
.text

main:
    li t0, 0
    li t1, 1
    li t5, 0 #temp
    li t3, 8 #contador de execucoes

loop:
    bge zero, t3, fim
    addi t3, t3, -1
    add t5, t1, t0
    mv t0, t1
    mv t1, t5
    j loop

fim:
    mv a1, t1
    li a0, 1
    ecall
