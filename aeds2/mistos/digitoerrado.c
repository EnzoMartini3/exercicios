#include <stdio.h>

void fazedor(int num, int i){
    int vet[500];
    int tam = 0;
    for(int z=i; z>0; z%10){
        vet[tam] = z % ();
        tam++;
    }

    for(int z=0; z<tam; z++){
        printf("%i", vet[z]);
    }
}

int main(){
    int num, entrada;
    while(scanf("%i  %i", &num, &entrada) == 2){
        fazedor(num, entrada);
    }
    return 0;
}