# void swap(int v[], int k)
# Registros:
# a0 = v (endereço base)
# a1 = k (índice)
# t0 = temp (variável)


swap:
    slli t1, a1, 2          # shiftando 4 bits: t1 = a1 << 2
    add t2, a0, t1          # t2 agora contém o endereço de v[k]

    lw t0, 0(t2)            # t0 = v[k]
    lw t3, 4(t2)            # t3 = v[k+1]
    sw t3, 0(t2)            # v[k] = t3 (v[k+1])
    sw t0, 4(t2)            # v[k+1] = t0 (temp)

    jr ra







#    section .data
#    ; Vetor de 4 inteiros (4 bytes cada)
#    meu_vetor dd 10, 20, 30, 40
#    ; dd = Define Double-word (4 bytes)
#    ; dw = Define Word (2 bytes)
#    ; db = Define Byte (1 byte)
