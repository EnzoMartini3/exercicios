#include <stdio.h>
#include <string.h>

void inverte(char frase[], int i, int f){
    if((i<f)){ //corre ate a metade da string
        char temp = frase[i];
        frase[i] = frase[f];
        frase[f] = temp;
        inverte(frase, i+1, f-1); //reinicia e aproxima i e f
    }
}

int main(){

    char frase[500];
    int i,f,stop=0;
    do {
        scanf("%s", frase);

        if (strcmp(frase, "FIM") == 0) break; // Se for "FIM", encerra

        int f = strlen(frase) - 1; // Posição do último caractere
        inverte(frase, 0, f);

        printf("%s\n", frase); // Imprime a string invertida
    } while (1);

    return 0;
}