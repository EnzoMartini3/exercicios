#include <stdio.h>

int tamanhoString(char vet[]){
    int a=0;
    while(vet[a] != '\0'){ //até string acabar
        a++;
    }
    return a;
}

int substring(char vet[]){
    int tam = tamanhoString(vet);
    int maior = 0;
    for(int i=0; i<tam; i++){ //ver todos os caracteres da string
        char visto[200];
        for(int k = 0; k < 200; k++){visto[k] = 0;} //reiniciar visto[]
        int n = 0;
        for(int j=i; j<tam; j++){ //procuramos dentro de visto[] pelo caracter atual. Se estiver lá, significa que repetiu.
            int repetido = 0;
            for(int k=0; k<n; k++){
                if(vet[j] == visto[k]){
                    repetido = 1;
                    break;
                }
            }
            if(repetido){
                break; //repetiu caracter, entao a sequencia é reiniciada
            }
            visto[n] = vet[j]; //nao encontrado, registramos a nova letra e continuamos sequencia
            n++;
            if(n>maior){
                maior = n;
            }
        }
    }
    return maior;
}

int main(){
    char vet[200];
    while(scanf("%s", vet) == 1 && !(vet[0] == 'F' && vet[1] == 'I' && vet[2] == 'M' && vet[3] == '\0')){
        printf("%i\n", substring(vet));
    }
    return 0;
}