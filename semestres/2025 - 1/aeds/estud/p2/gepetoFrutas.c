/*Um liquidificador inteligente separa ingredientes inseridos pela categoria: Frutas, Vegetais, Grãos.

Você deve desenvolver um programa que leia vários ingredientes, com a categoria ("fruta", "vegetal" ou "grao") e o nome (ex: "banana"). Os ingredientes devem ser listados agrupados por categoria, em ordem alfabética dentro de cada grupo.

> Entrada:
fruta banana  
vegetal cenoura  
grao aveia  
fruta maca  
grao chia
fim

> Saída:
Frutas: banana, maçã, ...
Vegetais: cenoura, ...
Grãos: aveia, ... */

#include <stdio.h>
#include <string.h>

typedef struct liquid{
    char fru[50][10];
    char veg[50][10];
    char grao[50][10];
    int f, v, g;
} liquid;

void alfa(char vet[50][10], int n) {
    char temp[10];
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (strcmp(vet[i], vet[j]) > 0) {
                strcpy(temp, vet[i]);
                strcpy(vet[i], vet[j]);
                strcpy(vet[j], temp);
            }
        }
    }
}

void cat(char tipo[], char nome[], liquid *l){
    if(strcmp(tipo, "fruta")){
        strcpy(l->fru[l->f], nome);
        f++;
    }else if(strcmp(tipo, "vegetal")){
        strcpy(l->veg[l->v], nome);
        v++;
    }else if(strcmp(tipo, "grao")){
        strcpy(l->grao[l->g], nome);
        g++;
    }
}

int main(){
    Liquid l = {.f = 0, .v = 0, .g = 0};
    char nome[50];
    char tipo[50];
    
    do{
        scanf("%s %s", tipo, nome);
        cat(tipo, nome);
    }while(tipo!="fim");

    alfa(fru);
    alfa(veg);
    alfa(grao);

    printf("Frutas: ");
    for(int i = 0; i < l.f; i++){
        printf("%s", l.fru[i]);
    }

    printf("Vegetais: ");
    for(int i = 0; i < l.v; i++){
        printf("%s", l.veg[i]);
    }

    printf("Graos: ");
    for(int i = 0; i < l.g; i++){
        printf("%s", l.grao[i]);
    }
        
    return 0;

}


