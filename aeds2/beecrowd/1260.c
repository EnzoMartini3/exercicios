#include <stdio.h>
#include <stdlib.h>

typedef struct Celula{
    struct Celula* prox;
    char nome[50];
    int num;
}Celula;

typedef struct Lista{
    Celula* prim;    
    Celula* ult;
}Lista;

int cmp(char dest[], char org[]){
    int i=0;
    while(org[i] != '\0'){
        if(org[i] != dest[i]){
            return 0;
        }
        i++;
    }
    return 1;
}

void strcopy(char dest[], char org[]){
    int i=0;
    while(org[i] != '\0'){
        dest[i] = org[i];
        i++;
    }
    dest[i] = '\0';
}

Lista* newLista(){
    Lista* l = (Lista*)malloc(sizeof(Lista));
    Celula* cel = (Celula*)malloc(sizeof(Celula));
    l->prim = cel;
    l->ult = cel;
    return l;
}

Celula* newCelula(Lista* l, char name[]){
    Celula* cel = (Celula*)malloc(sizeof(Celula));
    l->ult->prox = cel;
    l->ult = cel;
    strcopy(cel->nome, name);
    cel->num = 1;
    cel->prox = NULL;
    return cel;
}


Celula* compara(Lista* l, char entrada[]){
    Celula* cel = l->prim->prox;
    while(cel!=NULL){
        if(cmp(entrada, cel->nome) == 1){
            return cel;
        }
        cel = cel->prox;
    }
    return l->prim;
}

int main(){
    Lista* l = newLista();
    char entrada[50];
    int tamanho = 0;
    scanf("%s", entrada);
    while(entrada[0] != 'F' && entrada[1] != 'I' && entrada[2] != 'M'){
        tamanho++;
        Celula* comp = compara(l, entrada);

        if(comp != l->prim){
            comp->num++;
        }else{
            comp = newCelula(l, entrada);
        }

        scanf("%s", entrada);
    }

    //mostrar todos
    Celula* mostrador = l->prim->prox;
    float pct;
    while(mostrador!=NULL){
        pct = 100.0 / tamanho * mostrador->num;
        printf("%s %.4f ", mostrador->nome, pct);
        mostrador = mostrador->prox;
    }
}