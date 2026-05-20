#include <stdio.h>
#include <stdlib.h>

typedef struct Celula{
    int elemento;
    struct Celula* prox;
}Celula;

typedef struct Lista{
    Celula* prim;
    Celula* ult;
}Lista;

Celula* newCelula(Lista* l, int x){
    Celula* cel = (Celula*)malloc(sizeof(Celula));
    l->ult->prox = cel;
    l->ult = cel;
    cel->elemento = x;
    cel->prox = NULL;
    return cel;
}

Lista* newLista(){
    Lista* l = (Lista*)malloc(sizeof(Lista));
    Celula* cel = (Celula*)malloc(sizeof(Celula));
    l->prim = cel;
    l->ult = cel;
    cel->prox = NULL;
    return l;
}

int main(){
    int hash, n;
    scanf("%i %i", &hash, &n);
    if(hash == 0 || n == 0){
        return 0;
    }
    Lista* listas[hash];
    for(int i=0; i<n; i++){
        int atual;
        scanf("%i", &atual);
        int h = atual % hash;
        Lista* l = newLista();
        listas[h] = l;
        Celula* criador = l->ult;
        criador = newCelula(l, atual);
    }

    //mostrar
    for(int z=0; z<n; z++){
        printf("%i -> ", z);
        Celula* mostrador = listas[z]->prim->prox;
        while(mostrador != NULL){
            printf("%i -> ", mostrador->elemento);
            mostrador = mostrador->prox;
        }
        printf("/");
    }
    return 0;
}