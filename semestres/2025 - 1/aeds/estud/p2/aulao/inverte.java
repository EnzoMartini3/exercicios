//Faça um método da classe Lista que inverte a ordem atual da lista, sem criar uma lista nova. (inverter as celulas :O)

public void inverte(){
    Celula i=null;
    Celula j=null;
    Celula atual=inicio;
    fim=inicio;
    while(atual!=null){
        i=atual.prox; //"proximo"
        atual.prox=j;
        j=atual; //"anterior"
        atual=i;
    }
    inicio=j;
}