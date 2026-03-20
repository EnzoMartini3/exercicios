#include <stdio.h>
#include <string.h>
#include <ctype.h>

// Função que verifica se é palíndromo
int palindromo(char frase[]) {
    int len = strlen(frase);
    for (int i = 0; i < len / 2; i++){ // Comparar caracteres
        if (frase[i] != frase[len - i - 1]) {
            return 0; //Não é palíndromo
        }
    }
    return 1; // É palíndromo
}

int main() {
    char frase[1000];
    do {
        fgets(frase, sizeof(frase), stdin);
        frase[strcspn(frase, "\n")] = '\0'; // Remover a quebra de linha

        if (strcmp(frase, "FIM") != 0) { // Se não for "FIM", processa a string
            printf("%s\n", palindromo(frase) ? "SIM" : "NAO");
        }
    } while (strcmp(frase, "FIM") != 0);
    return 0;
}
