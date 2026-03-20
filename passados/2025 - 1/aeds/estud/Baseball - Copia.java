import java.io.*;
import java.util.*;

public class Cel{
    int elemento;
    Cel prox;
}

Cel(int e){
    this.elemento = e;
    this.prox = null;
}

public class Pilha{
    Cel inicio;
    Cel ult;
}

Pilha(){
    this.inicio = null;
    this.penult = null;
}

public class Baseball {

    void letrac(Pilha p){
        
    }

    int letrad(Pilha p){

    }

    int mais(Pilha p){

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String vet = sc.nextLine();
        String[] partes = vet.split(",");
        Pilha p = new Pilha();
        for(){
            switch(partes[i]){
                case "C":
                    letrac(p);
                break;
                case "D":
                    letrad(p);
                break;
                case "+":
                    mais(p);
                break;
                default:
                    int pepe = Integer.parseInt(partes[i]); //se for numero passa pra int
                    Celula p(pepe) = new Celula; //nova celula com o bagui
                break;
            }
        }
        Celula p = inicio;
        int res;
        while(p!=null){
            res
        }
    }
}

/* import java.util.*;

class Cel {
    int elemento;
    Cel prox;

    Cel(int e) {
        this.elemento = e;
        this.prox = null;
    }
}

class Pilha {
    Cel topo;

    Pilha() {
        this.topo = null;
    }

    void empilhar(int x) {
        Cel nova = new Cel(x);
        nova.prox = topo;
        topo = nova;
    }

    int desempilhar() {
        if (topo == null) return 0;
        int val = topo.elemento;
        topo = topo.prox;
        return val;
    }

    int topo() {
        if (topo == null) return 0;
        return topo.elemento;
    }

    int segundo() {
        if (topo == null || topo.prox == null) return 0;
        return topo.prox.elemento;
    }

    int somaTotal() {
        int soma = 0;
        Cel tmp = topo;
        while (tmp != null) {
            soma += tmp.elemento;
            tmp = tmp.prox;
        }
        return soma;
    }
}

public class Baseball {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine().replaceAll("\\[|\\]|\"", "");
        String[] ops = entrada.split(",");

        Pilha p = new Pilha();

        for (String op : ops) {
            switch (op.trim()) {
                case "C":
                    p.desempilhar();
                    break;
                case "D":
                    p.empilhar(2 * p.topo());
                    break;
                case "+":
                    int a = p.topo();
                    int b = p.segundo();
                    p.empilhar(a + b);
                    break;
                default:
                    p.empilhar(Integer.parseInt(op.trim()));
                    break;
            }
        }

        System.out.println(p.somaTotal());
    }
}
 */