#include <stdio.h>

void dimas(char entrada[]){
    int tam = 0;
    int esq = 0;
    int dir = 0;
    for(int i=0; entrada[i] != '\0'; i++){
        tam++;
    }

    for(int a=0; a<tam; a++){
        if(entrada[a] == '<'){
            esq++;
        }else if(entrada[a] == '>'){
            dir++;
        }
    }

    printf("Dir: %i Esq: %i", dir, esq);

    if(dir>esq){
        printf("%i", (dir-esq));
    }else{
        printf("%i", (esq-dir));
    }

}

int main(){
    int num;
    char entrada[1001];
    scanf("%i", &num);
    for(int i=0; i<num; i++){
        scanf(" %s", entrada);
        dimas(entrada);
    }

    return 0;
}