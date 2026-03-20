.text
.globl main

main:
    # --- 1. Carregar X da Memória ---
    la t0, X       # t0 = Endereço da variável X
    lw a0, 0(t0)   # a0 = Valor de X (usamos a0 para x)
    
    # --- 2. Verificar Paridade ---
    # Paridade é determinada pelo bit menos significativo (LSB):
    # LSB = 0 -> Par
    # LSB = 1 -> Ímpar
    
    # Isolar o LSB usando AND com 1 (0x1)
    # Resultado: t1 = 0 (Par) ou t1 = 1 (Ímpar)
    andi t1, a0, 1
    
    # Se t1 for diferente de zero (ímpar), salta para o cálculo ímpar
    bne t1, zero, Impar_case
    
    # --- CASO PAR: y = x^4 + x^3 - 2x^2 ---
    
Par_case:
    # a0 (x) será preservado
    # Registradores temporários para potências:
    # t2 = x^2
    # t3 = x^3
    # t4 = x^4
    
    # x^2
    mul t2, a0, a0        # t2 = x * x (x^2)
    
    # x^3
    mul t3, t2, a0        # t3 = x^2 * x (x^3)
    
    # x^4
    mul t4, t3, a0        # t4 = x^3 * x (x^4)
    
    # -2x^2
    # Multiplicar x^2 por 2, depois subtrair (sub) ou usar sub com 0 para negar
    addi t5, zero, 2      # t5 = 2
    mul t5, t5, t2        # t5 = 2 * x^2
    
    # y = x^4 + x^3 - 2x^2
    add a1, t4, t3        # a1 = x^4 + x^3
    sub a1, a1, t5        # a1 = (x^4 + x^3) - 2x^2. Resultado y em a1.
    
    j Store_Y             # Salta para armazenar o resultado

    
    # --- CASO ÍMPAR: y = x^5 - x^3 + 1 ---

Impar_case:
    # a0 (x)
    # Reutilizando t2, t3, t4:
    
    # x^2 (calculado novamente, pois o caso Par não foi executado)
    mul t2, a0, a0        # t2 = x^2
    
    # x^3
    mul t3, t2, a0        # t3 = x^3
    
    # x^4
    mul t4, t3, a0        # t4 = x^4
    
    # x^5
    mul t5, t4, a0        # t5 = x^5
    
    # y = x^5 - x^3 + 1
    sub a1, t5, t3        # a1 = x^5 - x^3
    addi a1, a1, 1        # a1 = (x^5 - x^3) + 1. Resultado y em a1.

    
    # --- 3. Armazenar Y na Memória e Fim ---

Store_Y:
    # O endereço de X já está em t0 (la t0, X).
    # O endereço de Y é o endereço de X + 4 bytes.
    addi t0, t0, 4        # t0 = Endereço de Y (X + 4)
    
    # Salvar o resultado y (em a1) no endereço Y
    sw a1, 0(t0)          # Y = a1 (Resultado de y)
    
    # --- Fim do Programa (Opcional: Imprime Y para verificação) ---
    
    # Para imprimir o resultado (y está em a1)
    mv a1, a1             # a1 já tem o valor de Y
    li a0, 1              # Syscall code 1 (Print Integer)
    ecall
    
    li a0, 10             # Syscall code 10 (Exit)
    ecall