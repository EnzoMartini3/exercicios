#include <stdio.h>

int somador(int num){ //chamamos recursivamente essa mesma funcao, adicionando ao parametro esse mesmo numero divido por 10, o que remove uma unidade dele por vez. assim, ele é capaz de acumular os valores de cada dezena, até o zero
    if(num == 0){
        return 0;
    }
    return (num % 10) + somador(num / 10);
}

int main(){
    int num;
    while(scanf("%i", &num) == 1){ //se algo além de um numero for digitado, o scanf devolve falso (0) e para o programa
        printf("%i\n", somador(num)); //chama a funcao
    }
    return 0;
}