#include <stdio.h>

char* inverte(char v[200], char nova[200]){ //recebemos os vetores e usamos um while pra calcular o tamanho do vetor v, e guardamos em n e tam. então percorremos as strings criando em nova a string oposta.
    int n=0;
    while(v[n] != '\0'){
        n++;
    }

    int tam = n;
    for(int i=0; i<tam; i++){
        nova[i] = v[n];
        n--;
    }
    return nova;
}

int main(){ //criamos 2 vetores, o que recebe a string e um temporário para que o programa o altere e retorne mais tarde (nao consegui fazer a funcao retornar um vetor criado dentro dela).
    char entrada[200];
    char nova[200];
    scanf(" %[^\n]", entrada); //ler caracteres até a quebra de linha
    while(!(entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M' && entrada[3] == '\0')){
        printf("%s", inverte(entrada, nova));
        scanf(" %[^\n]", entrada);
    }
    return 0;
}