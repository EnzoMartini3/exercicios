.data
A: .word -5

.text

main:
    la t5, A  #carregar para t5 o endereço em A (equivale a -5)
    lw t0, 0(t5)  #load word
    jal ra, posneg

    sw t0, 0(t5)
    mv a1, t0\
    li a0, 1
    ecall


posneg:
    bgt t0, zero, fim
    li t1, -1
    mul t0, t1, t0

fim:
    jr ra