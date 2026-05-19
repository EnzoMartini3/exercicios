class Lista{
    Celula inicio;
    Celula fim;

    public Celula maiorPilha(){
        Celula aux = inicio;
        int maiorT = 0;
        Celula maiorRef;
        Celula temp;
        while(aux != fim){
            CelulaPilha p = topo;
            temp = topo;
            int i = 0;
            while(p != null){
                p = p.prox;
                i++;
            }
            if(i > maiorT){
                maiorRef = temp;
            }
            aux = aux.prox;
        }
    }
}

class Celula{
    CelulaPilha topo;
    Celula prox;
}

class CelulaPilha{
    int elemento;
    CelulaPilha prox;
}