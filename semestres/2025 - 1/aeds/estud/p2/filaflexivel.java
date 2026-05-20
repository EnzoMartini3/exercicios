public class Celula{
    int elemento;
    Celula prox;

    Celula(int elemento){
        this.elemento = elemento;
        this.prox = null;
    }
}

class filaflexivel{
    Celula primeiro, ultimo;

    public filaflexivel(){
        primeiro=ultimo=new Celula;
    }

    void inserirFim(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }
    int removerInicio(){
        if(primeiro==ultimo){
            Environment.Exit(0);
        }
        Celula tmp = primeiro; //cria um novo ponteiro
        primeiro = primeiro.prox;
        int x = tmp.elemento;
        tmp.prox = null;
        tmp = null;
        return x;
    }
}