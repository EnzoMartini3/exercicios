class CelulaDupla{
    int elemento;
    CelulaDupla prox, ant;
}

CelulaDupla intercalarReverso(CelulaDupla c1, CelulaDupla c2){
    while(c1.prox!=null){
        c1=c1.prox; //final
    }
    while(c2.prox!=null){
        c2=c2.prox; //final
    }
    int p=0; //contador pra saber se c1/c2 acabaram
    CelulaDupla c3 = new CelulaDupla; //celula cabeca
    CelulaDupla cd3 = c3; //retorno
    while(p!=2){
        if(!(c1==null)){
            c3.prox = new CelulaDupla; // nova celula
            c3.prox.elemento = c1.elemento; //coloca o elemento do outro ponteiro
            c3.prox.ant=c3; //cria ant para nova celula
            c3.ant.prox=c3; //cria prox para o anterior
            c3=c3.prox; //passa c3 para nova celula
            c3.prox = null; //faz apontar pra nulo
            c1=c1.ant; //volta o c1
        }else{
            p++;
        }
        if(!(c2==null)){
            c3.prox = new CelulaDupla;
            c3.prox.elemento = c2.elemento;
            c3.prox.ant=c3;
            c3.ant.prox=c3;
            c3=c3.prox;
            c3.prox = null;
            c2=c2.ant;
        }else{
            p++;
        }
    }
    return cd3;

}