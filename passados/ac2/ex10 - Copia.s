li a0, 8              # Syscall code 8 (Read String)
    la a1, buffer         # a1 = Endereço do buffer (onde salvar a entrada)
    li a2, 100            # a2 = Tamanho máximo do buffer
    ecall

mv t0, a1             # t0 = Ponteiro de leitura (começa no buffer)
    
find_end:
    lb t1, 0(t0)          # t1 = Caractere atual
    beq t1, zero, found_null # Se for '\0', já é o fim (caso de buffer cheio)
    addi t0, t0, 1        # Avança o ponteiro
    j find_end

found_null:
    addi t0, t0, -1       # Volta um byte (aponta para o '\n')
    sb zero, 0(t0)        # Coloca '\0' no lugar do '\n'
    
    # --- 4. Chama a função strrev ---
    la a0, buffer         # a0 = Endereço da string (Argumento para strrev)
    jal ra, strrev        # Chama a função
    
    # --- 5. Imprime a String Invertida ---
    li a0, 4              # Syscall code 4 (Print String)
    la a1, msg_saida      # Imprime a mensagem de saída
    ecall

    la a1, buffer         # a1 = Endereço da string (já invertida)
    ecall                 # Imprime a string invertida

    # --- 6. Encerra o Programa ---
    li a0, 10
    ecall

# ------------------------------------------------------------------
# void strrev(char *s)
# a0: Endereço inicial da string (s)
# ------------------------------------------------------------------
strrev:
    # Salvar registradores salvos pelo chamador (s0, s1, ra)
    addi sp, sp, -12
    sw s0, 8(sp)
    sw s1, 4(sp)
    sw ra, 0(sp)
    
    # s0: Ponteiro 'start' (mantém o endereço inicial)
    # s1: Ponteiro 'end' (aponta para o último caractere)
    # t0: Caractere temporário
    
    mv s0, a0             # s0 = start (endereço inicial)
    
    # 1. Encontrar o fim da string (ponteiro 'end')
    mv s1, a0             # s1 começa em 'start'
    
find_end_char:
    lb t0, 0(s1)          # t0 = Caractere atual
    beq t0, zero, found_end_str # Se for '\0', encontramos o fim.
    addi s1, s1, 1        # Avança o ponteiro
    j find_end_char
    
found_end_str:
    addi s1, s1, -1       # s1 = end (volta 1 byte, agora aponta para o último caractere válido)
    
    # 2. Loop de Troca
swap_loop:
    # Condição de saída: start >= end
    bge s0, s1, swap_fim  # Se s0 >= s1, a inversão está completa.

    # -- SWAP (Troca) --
    
    # 1. Carregar s[start]
    lb t0, 0(s0)          # t0 = s[start]
    
    # 2. Carregar s[end]
    lb t1, 0(s1)          # t1 = s[end]
    
    # 3. Armazenar s[end] em s[start]
    sb t1, 0(s0)          # s[start] = t1 (s[end])
    
    # 4. Armazenar s[start] (original) em s[end]
    sb t0, 0(s1)          # s[end] = t0 (s[start] original)
    
    # -- Atualizar Ponteiros --
    addi s0, s0, 1        # start++
    addi s1, s1, -1       # end--
    j swap_loop

swap_fim:
    # Restaurar registradores salvos e retornar
    lw ra, 0(sp)
    lw s1, 4(sp)
    lw s0, 8(sp)
    addi sp, sp, 12       # Libera o espaço da pilha
    jr ra