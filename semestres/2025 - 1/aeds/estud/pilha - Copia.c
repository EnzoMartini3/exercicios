#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Celula{
    int elemento;
    struct Celula* prox;
}Celula;

Celula* newCelula(int x){
    Celula* nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = x;
    nova->prox = NULL;
    return nova;
}

typedef struct{
    Celula* topo;
}Pilha;

Pilha* newPilha(){
    Pilha* p = (Pilha*)malloc(sizeof(Pilha));
    p->topo = NULL;
    return p;
}

void push(int x, Pilha* p){
    Celula* nova = newCelula(x);
    nova->prox = p->topo;
    p->topo = nova;
}

int pop(Pilha* p){
    int x = p->topo->elemento;
    Celula* aux = p->topo;
    p->topo = aux->prox;
    free(aux);
    return x;
}

int min(Pilha* p){
    int x = p->topo->elemento;
    Celula* aux = p->topo;
    while(aux!=NULL){
        if(aux->elemento < x){
            x = aux->elemento;
        }
        aux = aux->prox;
    }
    return x;
}

int main(){
    Pilha* p=newPilha();
    int n,x=0;
    char com[20];
    scanf("%i",&n);
    while(n>0){
        scanf("%s", com);
        if(strcmp(com,"PUSH")==0){
            scanf("%d", &x);
            push(x,p);
        }else if(strcmp(com,"POP")==0){
            pop(p);
        }else if(strcmp(com,"MIN")==0){
            min(p);
        }
        n--;
    }

}