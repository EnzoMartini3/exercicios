typedef struct{
    int elemento;
    No* esq, dir;
}No;

typedef struct{
    No* raiz;
}Arvore;

No* newNo(int x){
    No* n = (No*)malloc(sizeof(No));
    n->elemento = x;
    n->esq = NULL;
    n->dir = NULL;
    return n;
}

Arvore* iniciArvore(){
    Arvore* a = (Arvore*)malloc(sizeof(Arvore));
    a->raiz = NULL;
    return a;
}

void inserir(int x){
    raiz = inserir(x, raiz);
}
No* inserir(int x, No* i){
    if(x == NULL){
        i = newNo(x);
    }else if(x < i->elemento){
        i->esq = inserir(x, i->esq);
    }else if(x > i->elemento){
        i->dir = inserir(x, i->dir);
    }
    return i;
}

void inserirPai(int x){}

bool pesquisar(int x){
    return pesquisar(x, raiz);
}
bool pesquisar(int x, No* i){
    bool resp = false;
    if(x == i->elemento){
        resp = true;
    }else if(x < i->elemento){
        return pesquisar(x, i->esq);
    }else if(x > i->elemento){
        return pesquisar(x, i->dir);
    }
    return resp;
}

void caminharCentral(No* i){
    if(i!=NULL){
        caminharCentral(i->esq);
        printf("%i ", i->elemento);
        caminharCentral(i->dir);
    }
}

void caminharPos(No* i){
    if(i!=NULL){
        caminharPos(i->esq);
        caminharPos(i->dir);
        printf("%i ", i->elemento);
    }
}

void caminharPre(No* i){
    if(i!=NULL){
        printf("%i ", i->elemento);
        caminharPos(i->esq);
        caminharPos(i->dir);
    }
}

void remover(int x){
    raiz = remover(i, raiz);
}
No* remover(int x, No* i){
    if(x < i->elemento){
        i->esq = remover(x, i->esq);
    }else if(x > i->elemento){
        i->dir = remover(x, i->dir);
    }else if(i->esq == NULL){
        i = i->dir;
    }else if(i->dir == NULL){
        i = i->esq;
    }else{
        i->esq = maiorEsq(i, i->esq);
    }
    return i;
}

No* maiorEsq(No* i, No* j){
    
    return j;
}