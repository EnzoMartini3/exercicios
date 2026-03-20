public void remover(){
    Celula aux = inicio;
    for(;aux.prox!=null;aux=aux.dir);
        aux.esq.dir=null;
        aux.esq=null;
        if(aux.inf!=null){
            aux.inf.sup=null;
            Celula aux2=aux.inf;
            aux.inf=null;
            aux=aux2;
        }
    aux=null;
}