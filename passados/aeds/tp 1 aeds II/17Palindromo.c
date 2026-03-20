#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// Função que verifica se a string é um palíndromo
bool palindromo(char frase[], int len, int i) {
    if (i >= len) { 
        return true; // Se chegou na metade sem falhar, é palíndromo
    }
    if (frase[i] != frase[len]) {
        return false; // Se encontrar caracteres diferentes, não é palíndromo
    }
    return palindromo(frase, len - 1, i + 1); // Verifica os próximos caracteres
}

int main() {
    char frase[500];

    do { 
        fgets(frase, sizeof(frase), stdin); // Lê a string com espaços e ENTER
        frase[strcspn(frase, "\n")] = '\0'; // Remove o '\n' do final da string

        if (strcmp(frase, "FIM") != 0) { // Impede que "FIM" seja verificado
            if (palindromo(frase, strlen(frase) - 1, 0)) { 
                printf("SIM\n");
            } else {
                printf("NAO\n");
            }
        }
    } while (strcmp(frase, "FIM") != 0); // Continua até "FIM" ser digitado

    return 0;
}
