#include <stdio.h>

int finaliza(char array[]){
    if (array[0] == 'F' && array[1] == 'I' && array[2] == 'M' && array[3] == '\0'){
        return 1;
    }
    return 0;
}

char* inverte(char v[200], char nova[200]){ //recebemos os vetores e usamos um while pra calcular o tamanho do vetor v, e guardamos em n e tam. então percorremos as strings criando em nova a string oposta.
    int n=0;
    while(v[n] != '\0'){
        n++;
    }

    int tam = n;
    for(int i=0; i<tam; i++){
        nova[i] = v[n-1]; // começa em n-1 para pular o '\0'
        n--;
    }
    nova[tam] = '\0'; // finaliza nova 
    return nova;
}

int main(){ //criamos 2 vetores, o que recebe a string e um temporário para que o programa o altere e retorne mais tarde (nao consegui fazer a funcao retornar um vetor criado dentro dela).
    char entrada[200];
    char nova[200];
    while(scanf("%[^\n]%*c", entrada) == 1 && !finaliza(entrada)){
        printf("%s\n", inverte(entrada, nova));
    }
    return 0;
}