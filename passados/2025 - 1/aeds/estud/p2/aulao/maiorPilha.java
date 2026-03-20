/* Considere uma estrutura Lista de Pilhas. Crie o metodo CelulaLista maiorPilha() na classe Lista, que retorna a Celula da Lista aponta para a pilha com o maior numero de elementos. Caso tenham pilhas do mesmo tamanho, retornar a primeira que aparece.

ILUSTRAÇÃO DA ESTRUTURA:

CEL -> CEL -> CEL -> CEL
|      |       |      |
v      v       v      v
CEL    CEL    CEL
|      |       |
v      v       v
CEL    CEL    CEL
|      |       
v      v       
CEL    CEL 

*/
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

int contar(CelulaLista x){
    CelulaPilha y = x.topo;
    int cont=0;
    while(y!=null){
        cont++;
        y=y.prox;
    }
    return cont;
}

CelulaLista maiorPilha(){
    CelulaLista x = inicio;
    CelulaLista resp = inicio;
    int maior = 0;

    while(x!=null){
        int atual = contar(x);
        if(atual>maior){
            maior=atual;
        }
        x=x.prox;
    }
    return resp;

}