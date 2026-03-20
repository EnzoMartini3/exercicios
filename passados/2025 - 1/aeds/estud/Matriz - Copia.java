import java.util.*;

class Celula{
    Celula inf,sup,dir,esq;
    int elemento;

    Celula(int e){
        this.inf = null;
        this.esq = null;
        this.dir = null;
        this.sup = null;
        this.elemento = e;
    }
}

public class Matriz{
    Celula inicio;
    int li,col;

    Matriz(int li, int col){
        this.li = li;
        this.col = col;
        iniciar();
    }

    void iniciar()


    Matriz soma(Matriz m){}
    Matriz multi(Matriz m){}
    void diag(){}
    void diagSec(){}
}