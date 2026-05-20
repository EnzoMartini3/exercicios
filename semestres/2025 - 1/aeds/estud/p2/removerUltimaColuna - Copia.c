typedef struct Celula{
    int elemento;
    struct Celula *inf,*dir,*esq,*sup;
} Celula;

typedef struct{
    int linhas, colunas;
    Celula *inicio;
} Matriz;

void removerUltimaColuna(Matriz *matriz){
    Celula *aux = matriz->inicio;
    for(int i=0; i<matriz->linhas; i++){
        for(int j=0; j<matriz->colunas-1; j++){
            aux=aux->dir;
        }
        aux->dir->esq=NULL;
        if(aux->dir->inf->sup!=NULL){
            aux->dir->inf->sup=NULL;
        }
        free(aux->dir);
        aux=aux->inf;
    }
    colunas--;
}