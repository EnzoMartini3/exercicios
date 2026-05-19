typedef struct{
    int numero;
    Celula *prox;
}Celula;

typedef struct{
    int numero;
    struct CelulaMatriz *prox, *ant;
    struct CelulaMatriz *inf, *sup;
}CelulaMatriz;

typedef struct{
    int numero;
    struct No *esq, *dir;
}No;

Celula* encontrarRepetidos(No *raiz, CelulaMatriz *inicio){}