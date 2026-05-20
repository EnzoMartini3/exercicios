#include <stdio.h>
#include <stdlib.h>

typedef struct{
    int id;
    char cmd[20];
}Acao;

typedef struct Celula{
    struct Celula* prox;
    Acao elemento;
}Celula;

typedef struct{
    Celula* prim;
    Celula* ult;
}Fila;

void iniciarFila(Fila *f){
    //cabeca
    Celula* cel = (Celula*)malloc(sizeof(Celula));
    cel->prox = NULL;
    f->prim = cel;
    f->ult = cel;
}

void enfileirar(Fila *f, char *linha){
    Acao x;
    sscanf(linha, "%i;%s", &x.id, x.cmd);
    Celula* cel = (Celula*)malloc(sizeof(Celula));
    f->ult->prox = cel;
    f->ult = cel;
    cel->prox = NULL;
    cel->elemento = x;
}

Acao remover(Fila *f){
    if(prim == ult){
        return NULL;
    }
    Celula* aux = f->prim->prox; //primeiro depois da cabeca
    Acao ret = aux->elemento;
    f->prim->prox = aux->prox;
    free(aux);  // prim     v
    return ret;  //cabeca -> 2 -> 90
}


int main() {
    Fila minhaFila;
    iniciarFila(&minhaFila);

    // Strings simulando a entrada do sistema de turnos
    char comandosTurno[3][40] = {
        "1;Atacar",
        "2;Curar",
        "3;Defender"
    };

    // 1. Enfileirando as ações
    for (int i = 0; i < 3; i++) {
        enfileirar(&minhaFila, comandosTurno[i]);
    }
    return 0;
}