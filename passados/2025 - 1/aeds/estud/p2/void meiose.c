#include <stdlib.h>

typedef struct Celula {
    int elemento;
    struct Celula *prox;
} Celula;

void meiose(){
    Celula* aux = inicio;
    int tmp;
    while(aux!=NULL){
        Celula *nova = (Celula*)malloc(sizeof(Celula));
        nova->prox = aux->prox;
        aux->prox = nova;
        if(aux->elemento!=NULL){
            aux->elemento /= 2;
            tmp = aux->elemento;
            nova->elemento = tmp;
        }
        aux = aux->prox;
    }
}