#include <stdio.h>

// Verifica se a string e "FIM"
int isFim(char s[]) {
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0');
}

// Metodo recursivo para Vogal
int isVogal(char s[], int i) {
    if (s[i] == '\0') return 1; // Chegou ao fim, todos eram vogais
    char c = s[i];
    if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
          c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')) {
        return 0; // Encontrou algo que nao e vogal
    }
    return isVogal(s, i + 1);
}

// Metodo recursivo para Consoante
int isConsoante(char s[], int i) {
    if (s[i] == '\0') return 1;
    char c = s[i];
    // Se for vogal ou nao for letra, nao e apenas consoante
    if ((c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
         c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') || 
        !((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
        return 0;
    }
    return isConsoante(s, i + 1);
}

// Metodo recursivo para Inteiro
int isInt(char s[], int i) {
    if (s[i] == '\0') return 1;
    if (s[i] < '0' || s[i] > '9') return 0; // Se nao for digito, falso
    return isInt(s, i + 1);
}

// Metodo recursivo para Real
int isReal(char s[], int i, int pontos) {
    if (s[i] == '\0') return 1;
    if (s[i] == '.' || s[i] == ',') {
        if (pontos > 0) return 0; // Mais de um separador decimal
        return isReal(s, i + 1, pontos + 1);
    }
    if (s[i] < '0' || s[i] > '9') return 0;
    return isReal(s, i + 1, pontos);
}

int main() {
    char entrada[1000];

    // Lendo a linha inteira (aceitando espacos ate o \n)
    while (scanf(" %[^\n]", entrada) == 1 && !isFim(entrada)) {
        
        // Teste Vogal
        if (isVogal(entrada, 0)) printf("SIM "); else printf("NAO ");
        
        // Teste Consoante
        if (isConsoante(entrada, 0)) printf("SIM "); else printf("NAO ");
        
        // Teste Inteiro
        if (isInt(entrada, 0)) printf("SIM "); else printf("NAO ");
        
        // Teste Real (passando contador de pontos/virgulas iniciado em 0)
        if (isReal(entrada, 0, 0)) printf("SIM\n"); else printf("NAO\n");
    }

    return 0;
}