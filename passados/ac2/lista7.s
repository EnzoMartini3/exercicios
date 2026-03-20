# ------------------------------------------------------------------
# Função: busca_caractere
# Objetivo: Encontra a primeira ocorrência de um caractere em uma string.
#
# Entrada:
#   a0 (x10): Endereço inicial da cadeia (ponteiro para o byte)
#   a1 (x11): Caractere a ser procurado (byte)
#
# Retorno (em a0):
#   - Endereço da primeira ocorrência do caractere
#   - 0 (zero) se o caractere não for encontrado
# ------------------------------------------------------------------

.text
.globl busca_caractere

busca_caractere:

    
loop_busca:
    lb t0, 0(a0)    # t0 = *a0 (caractere atual da string)
    beq t0, zero, not_found # Se t0 == 0 (null terminator), o caractere não foi encontrado.
    beq t0, a1, found       # Se t0 (caractere atual) == a1 (caractere procurado), salta para 'found'.
    addi a0, a0, 1          # a0 = a0 + 1 (Avança o ponteiro 1 byte)
    j loop_busca
    
    
# -----------------------------------------------------------
# Casos de Retorno
# -----------------------------------------------------------

found:
    # O registrador a0 contém o endereço onde o caractere foi encontrado.
    # Não precisa de mv, pois o valor já está em a0.
    jr ra                   # Retorna ao chamador.

not_found:
    # O caractrador não foi encontrado. Retorna 0.
    mv a0, zero             # a0 = 0
    jr ra                   # Retorna ao chamador.


# -----------------------------------------------------------
# Bloco Main de Teste (Exemplo de uso)
# -----------------------------------------------------------
.data
string_data: .string "hello world"  # String para teste (termina em '\0')

.text
.globl main

main:
    # Configurar a chamada da função 1: Procura 'w' (encontrado)
    la a0, string_data      # a0 = endereço de "hello world"
    addi a1, zero, 'w'      # a1 = caractere 'w' (ASCII 119)
    jal ra, busca_caractere # Chama a função. Retorna o endereço em a0.
    
    # Resultado do 1º teste (a0 deve ter o endereço de 'w')
    mv a1, a0               # a1 = Endereço encontrado (para imprimir)
    li a0, 1                # Syscall: Print Integer
    ecall
    
    # Configurar a chamada da função 2: Procura 'z' (não encontrado)
    la a0, string_data      # a0 = endereço de "hello world"
    addi a1, zero, 'z'      # a1 = caractere 'z' (ASCII 122)
    jal ra, busca_caractere # Chama a função. Retorna 0 em a0.
    
    # Resultado do 2º teste (a0 deve ter 0)
    mv a1, a0               # a1 = Resultado (0) para imprimir
    li a0, 1                # Syscall: Print Integer
    ecall
    
    li a0, 10               # Terminar o programa
    ecall