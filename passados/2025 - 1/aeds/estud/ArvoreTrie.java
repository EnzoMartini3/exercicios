class Celula{
    char elemento; //ambos tem elemento
    Celula prox;
    No no;
}

class No{
    char elemento;
    boolean fim;
    Celula prim;
    Celula ult;
}

class TRIE{
    No raiz;
}

void mostrar(){
    mostrar("",raiz);
}
void mostrar(String s, No i){
    //correr pela arvore imprimindo cada char
    //usar recursividade e ponteiros prim e ult
    //pula linha quando achar
    
    if(i.fim){/*printar s;*/ }
    if(i.primeiro!=i.ultimo){
        Celula c = i.primeiro.prox;
        while(c!=null){
            mostrar(s+c.elemento,c.no);
            c=c.prox;
        }
    }
}