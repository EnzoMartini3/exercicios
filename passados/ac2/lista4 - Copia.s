# int maximo(int v[], int n) {
#    int max = v[0];
#    for(i = 1; i < n; i++) {
#        if (v[i] > max){
#            max = v[i];
#        } 
#}   }

.text

# a0 = v (endereço base)
# a1 = n (tamanho do vetor)

func:
    addi s1, zero, 4
    lw s0, 0(a0)              # s0 = v[0] (Carrega o primeiro elemento como o máximo inicial), Endereço de v[0] é a0 + 0
    addi t0, zero, 1        # t0 = 1 (Índice de início do loop)

 loop_start:
    # 3. Condição do Loop: for(i = 1; i < n; i++); OU SEJA: se(i >= n) goto loop_end
    bge t0, a1, loop_end      # Se t0 (i) >= a1 (n), termina o loop

    # 4. Cálculo do Endereço de v[i]
    # Endereço v[i] = v + i * 4    
    slli t1, t0, 2            # t1 = t0 * 4 (i * 4, deslocamento)
    add t1, a0, t1           # t1 = a0 + t1 (Endereço de v[i])
    

    # 5. Carrega v[i]
    lw t2, 0(t1)              # t2 = v[i] (Carrega o elemento atual)
    
    
    # 6. Condição IF: if (v[i] > max)
    ble t2, s0, loop_increment # Se t2 (v[i]) <= s0 (max), pula o 'if'
    
    
    # 7. Corpo do IF: max = v[i];
    mv s0, t2                 # s0 (max) = t2 (v[i])
    
    
loop_increment:
    # 8. Incremento: i++
    addi t0, t0, 1            # t0 = t0 + 1 (i++)
    
    j loop_start                # Volta para o início do loop


loop_end:
    # 9. Valor de Retorno: Retorna max; O valor de retorno deve estar em a0.
    mv a0, s0                 # a0 = s0 (max)
    
    jr ra