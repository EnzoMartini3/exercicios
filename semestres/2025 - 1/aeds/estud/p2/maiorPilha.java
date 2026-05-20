class Lista{
    CelulaLista inicio;
    CelulaLista fim;
}
class CelulaLista{
    CelulaPilha topo;
    CelulaLista prox;
}
class CelulaPilha{
    int elemento;
    CelulaPilha prox;
}

CelulaLista maiorPilha() {    
    CelulaLista atual = inicio;
    CelulaLista maiorCel = inicio;
    int maiorTamanho = contarElementos(inicio.topo);
    
    while (atual != null) {
        int tamanhoAtual = contarElementos(atual.topo);
        if (tamanhoAtual > maiorTamanho) {
            maiorTamanho = tamanhoAtual;
            maiorCel = atual;
        }        
        atual = atual.prox;
    }
    
    return maiorCel;
}

int contarElementos(CelulaPilha topo) {
    int count = 0;
    CelulaPilha atual = topo;
    while (atual != null) {
        count++;
        atual = atual.prox;
    }
    return count;
}

/*
CelulaLista maiorpilha(){
    CelulaLista inif = inicio;
    CelulaPilha sb = inicio;
    CelulaLista maiorcel = inicio;
    int maiornum=0;
    do{
        for(maiornum=0;sb!=null;sb=sb.prox){
            for(int i=0;sb.elemento[i]!=null;i++){
                if(i>maiornum){
                    maiornum=i;
                    maiorcel=sb;
                }
            }
        }
    }(while(inif!=null))

    return maiorcel;
}
*/