//Seja a matriz de listas em Java apresentada abaixo, faça um método que remove todos os números impares existentes nas listas. Faça a analise de complexidade do seu metodo.

class Matriz{
    CelulaMat inicio;
    void removeLista(CelulaMat x){
        Celula tmp=new Celula();
        tmp.prox=x.primeiro;
        Celula z=tmp;
        while(z.prox!=null){
            if(z.prox.num%2!=0){
                z.prox=z.prox.prox;
            }else{
                z=z.prox;
            }
        }
        primeiro=tmp.prox;
    }
    void removeImpares(){
        CelulaMat x,y=inicio;
        while(y!=null){
            while(x!=null){
                removeLista(x);
                x=x.prox
            }
            y=y.inf;
            x=y;
        }
    }
}
class CelulaMat{
    CelulaMat prox, ant, sup, inf;
    Celula primeiro, ultimo;
    public CelulaMat(){
        prox = ant = sup = inf = null;
        primeiro = ultimo = new Celula();
    }
}
class Celula{
    int numero;
    Celula prox;
    public Celula(){ 
        this.elemento = 0;
        prox = null; 
    }
    public Celula(int elemento){
        this.numero = elemento;
        prox = null;
    }
}