class Arvore1{
    No raiz;
}

class Arvore2{
    No raiz;
}

class No {
    int elemento;
    No esq, dir;

    public No(int elemento) {
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }
}
class Celula{
    int elemento;
    Celula prox;
}

class Lista{
    Celula prim, ult;
}

class T1{
    int[] tabela;
    boolean[] reservado;
    T2 tabela2;

    T1(){
        this.tabela = new int[10];
        this.reservado = new boolean[10];
        this.tabela2 = new T2();
    }

    int hash(int x){
        return x%10;
    }

    void inserir(int x){
        int index = hash(x);
        if(reservado==false){
            tabela[index]=x;
            reservado[index]=true;
        }else{
            tabela2.inserir(x);
        }
    }

    boolean pesquisar(int x){
        int index = hash(x);
        if(reservado[index]==true){
            return true;
        }
        return tabela2.pesquisar(x);
    }
}

class T2{
    Lista l;
    Arvore1 a1;
    T3 t3;

    T2(){
        l = new Lista();
        a1 = new Arvore1();
        t3 = new T3();
    }

    int hash(int x){
        return x%3;
    }

    void inserir(int x){
        int index = hash(x);
        if(index==0){
            T3.inserir(x);
        }else if(index==1){
            l.inserir(x);
        }else{
            a1.inserir(x);
        }
    }

    boolean pesquisar(int x){
        int index = hash(x);
        if(index==0){
            T3.pesquisar(x);
        }else if(index==1){
            l.pesquisar(x);
        }else{
            a1.pesquisar(x);
        }
    }
}

class T3{ //hash com rehash
    Arvore2 a2;
    int[] tabela;
    
    int hash(int x){
        return x % 10;
    }

    int rehash(int x){
        return ++x % 10;
    }

    void inserir(){}

    boolean pesquisar(){}
}

class DoidonaSlides{
    void inserir(int x){
        inserir(x, )
    }
    boolean pesquisar(int x){}
    void remover(int x){}   
}