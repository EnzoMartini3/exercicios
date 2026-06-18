class AVL{
    No raiz;
    
    public AVL(){
        this.raiz=null;
    }
    
    public void inserir(int x){
        raiz = inserir(raiz, x);
    }
    
    public No inserir(No i, int x){
        if(i==null){
            i = new No(x);
        }else if(x > i.elemento){
            i.dir = inserir(i.dir, x);
        }else if(x < i.elemento){
            i.esq = inserir(i.esq, x);
        }
        i.setNivel();
        return i;
    }
    
    public void mostrar(No i){
        if(i!=null){
            mostrar(i.esq);
            System.out.println(i.elemento);
            mostrar(i.dir);
        }
    }
}

class No{
    int elemento;
    int nivel;
    No esq;
    No dir;
    
    public No(int x){
        this.elemento = x;
        this.nivel = 0;
        this.esq = null;
        this.dir = null;
    }
    
    public int pegarMaior(int no1, int no2){
        if(no1 > no2){
            return no1;
        }
        return no2;
    }
    
    public void setNivel(){
        this.nivel = 1 + pegarMaior(getNivel(this.dir), getNivel(this.esq));
    }
    
    public int getNivel(No i){
        if(i==null){
            return 0;
        }
        return i.nivel;
    }
}


public class AVLteste{
	public static void main(String[] args) {
		AVL a = new AVL();
		a.inserir(2);
		a.inserir(32);
		a.inserir(20);
		a.inserir(10);
		a.inserir(82);
		a.mostrar(a.raiz);
	}
}