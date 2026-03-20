import java.util.*;

class No{
    int elemento;
    No dir,esq;

    No(int elemento){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }
        No(int elemento, No esq, No dir){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }

}

public class Arvore{
    No raiz;
    Arvore(){ this.raiz = null; }

    void inserir(int x){
        raiz = inserir(x,raiz);
    }
    No inserir(int x, No i){
        if(i == null){
            i = new No(x);
        }if(x < i.elemento){
            i.esq = inserir(i.esq);
        }else if(x > i.elemento){
            i.dir = inserir(i.dir);
        }
        return i;
    }
    
    void inserirPai(int x) throws Exception{
        if(raiz == null){
            raiz = new No(x);
        }else if(x < raiz.elemento){
            inserirPai(x, raiz.esq, raiz);
        }else if(x > raiz.elemento){
            inserirPai(x , raiz.dir, raiz);
        }else{
            throw new Exception("Erro");
        }
    }
    void inserirPai(int x, No i, No pai){
        if(i == null){
            if(x < pai.elemento){
                pai.esq = new No(x);
            }else{
                pai.dir = new No(x);
            }
        }else if(x < i.elemento){
            inserirPai(x, i.esq, i);
        }else if(x > i.elemento){
            inserirPai(x, i.dir, i);
        }
    }
    
    boolean pesquisar(int x){
        return pesquisar(x, raiz);
    }
    boolean pesquisar(int x, No i){
        boolean resp;
        if(raiz == null){
            resp = false
        }else if(x == raiz.elemento){
            resp = true;
        }else if(x < raiz.elemento){
            resp = pesquisar(x, raiz.esq);
        }else(x > raiz.elemento){
            resp = pesquisar(x, raiz.dir);
        }
        return resp;
    }
    
    void caminharCentral(No i){
        if(i!=null){
            caminharCentral(i.esq);
            System.out.print(i.elemento + "");
            caminharCentral(i.dir);
        }
    }
    
    void caminharPre(No i){
        if(i!=null){
            System.out.print(i.elemento + "");
            caminharCentral(i.esq);
            caminharCentral(i.dir);
        }
    }
    
    void caminharPos(No i){
        if(i!=null){
            caminharCentral(i.esq);
            caminharCentral(i.dir);
            System.out.print(i.elemento + "");
        }
    }

    void remover(int x){
        raiz = remover(x, raiz);
    }
    No remover(int x, No i){
        if(x < i.elemento){
            i.esq = remover(x, i.esq);
        }else if(x > i.elemento){
            i.dir = remover(x, i.dir);
        }else if(i.esq==null){
            i = i.dir;
        }else if(i.dir==null){
            i = i.esq;
        }else{
            i.esq = maiorEsq(i, i.esq);
        }   
        return i;
    }
    No maiorEsq(No i, No j){
        if(j.dir==null){
            i.elemento = j.elemento;
            j = j.esq;
        }else{
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    int getMaior(){
        int resp = -1;
        No i = raiz;
        while(resp.dir!=null){
            i = i.dir;            
        }
        resp = i.elemento;
        return resp;
    }

    int getMenor(){
        int resp = -1;
        No i = raiz;
        while(resp.esq!=null){
            i = i.esq;            
        }
        resp = i.elemento;
        return resp;
    }

    int getAltura(No i){
        int h = 0, altEsq, altDir;
        if(i == null){
            return 0;
        }else{
            altEsq = 1 + getAltura(i.esq);
            altDir = 1 + getAltura(i.dir);
        }
        if(altEsq > altDir){
            h = altEsq;
        }else{
            h = altDir;
        }
        return h;
    }

    int getAlturaEsq(No i){
        int altEsq;
        if(i == null){
            return 0;
        }else{
            altEsq = 1 + getAlturaEsq(i.esq);
        }
        return altEsq;
    }

    int getAlturaDir(No i){
        int altDir;
        if(i == null){
            return 0;
        }else{
            altDir = 1 + getAlturaDir(i.esq);
        }
        return altDir;
    }

    int somarArvore(){
        return somarArvore(raiz);
    }
    int somarArvore(No i){
        int resp = 0;
        if(i!=null){
            resp = i.elemento + somarArvore(i.esq) + somarArvore(i.dir);
        }
        return resp;
    }

    int pares(){
        return pares(raiz);
    }
    int pares(No i){
        int resp = 0;
        if(i!=null){
            resp = ((i.elemento % 2 == 0) ? 1 : 0) + pares(i.esq) + pares(i.dir);
        }
        return resp;
    }

    No rotacionarEsq(No no){           //             Avo
        No noDir = no.dir;             //           /
        No noDirEsq = noDir.esq;        //        Pai
        noDir.esq = no;                //       /
        no.dir = noDirEsq;              // Filho
        return noDir;
    }

    No rotacionarDir(No no){
        No noEsq = no.esq;             
        No noEsqDir = noEsq.dir;       
        noEsq.dir = no;                
        no.esq = noEsqDir;             
        return noEsq;
    }

// COTOVELO: Rotacionar o pai pra ficar  Pai/Filho/Avo, e depois Rotaciona o Avo
//         Avo
//       /
//   Pai
//       \
//         Filho

    No rotacionarEsqDir(No no){
        no.esq = rotacionarEsq(no.esq);
        return rotacionarDir(no);
    }

    No rotacionarDirEsq(No no){
        no.dir = rotacionarDir(no.dir);
        return rotacionarEsq(no);
    }

    void balancear(){
        if(raiz.esq!=null && raiz.dir!=null){
           // 213, 231
        }else if(raiz.dir!=null){
            if(raiz.dir.dir!=null){
                raiz = rotacionarEsq(raiz); //123
            }else{
                raiz = rotacionarEsqDir(raiz); //132
            }
        }else{
            if(raiz.esq.esq!=null){
                raiz = rotacionarDir(raiz); //321
            }else{
                raiz = rotacionarDirEsq(raiz); //312
            }
        } 
    }

    void ler3nums(int a, int b, int c){
        inserir(a);
        inserir(b);
        inserir(c);
        balancear();
    }
}