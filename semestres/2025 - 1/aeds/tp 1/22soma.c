#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// Função recursiva para somar os dígitos de um número
int somaDigitos(int n) {
    if (n == 0) 
        return 0; 
    return (n % 10) + somaDigitos(n / 10); // Soma o último dígito e chama a função para o restante do número
}

int main() {
    char entrada[20]; // String para armazenar a entrada

    while (1) {
        scanf("%s", entrada); // Lê a entrada como string

        if (strcmp(entrada, "FIM") == 0) // Se for "FIM", encerra
            break;

        int num = atoi(entrada); // Converte para inteiro
        printf("%d\n", somaDigitos(num)); // Chama a função e imprime o resultado
    }

    return 0;
}
