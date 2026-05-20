/**
 * 
 * Leet 110
 * 
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

import java.util.*;

class Pilha{
    Celula prim;

    Pilha(){
        this.prim = null;
    }

    boolean isEmpty() {
        return prim == null;
    }

    void push(int num) {
        Celula nova = new Celula(num);
        nova.prox = prim;
        prim = nova;
    }

    int pop() {
        int valor = prim.num;
        prim = prim.prox;
        return valor;
    }
}

class Celula{
    Celula prox;
    int num;

    Celula(int num){
        this.num = num;
    }
}

class Arvore{
    No raiz;

    Arvore(No raiz){
        this.raiz = raiz;
    }
}

class No{
    int num;
    No esq;
    No dir;

    No (int n){
        this.num = n;
    }
    No (int n, No e, No d){
        this.num = n;
        this.esq = e;
        this.dir = d;
    }
}

public class ArvoreBalanceada {
    public int getAltura(No no){
    int h = 0;
    if(no != null){
        int alturaDir = 1 + getAltura(no.dir);
        int alturaEsq = 1 + getAltura(no.esq);
        if(alturaEsq < alturaDir){
            h = alturaDir;
        }else{
            h = alturaEsq;
        }
    }

    return h;
}

    void montar(No no, Pilha p){
        while(!p.isEmpty()){
            if(no.esq==null && !p.isEmpty()){
                int val = p.pop();
                no.esq = new No(val);
            }else if(no.dir==null && !p.isEmpty()){
                int val = p.pop();
                no.dir = new No(val);
            }else{
                montar(no.esq, p);
            }
        }
    }

    public boolean isBalanced(No raiz) {
        if(raiz.num==null){
            return true;
        }
        return (getAltura(raiz.dir)-getAltura(raiz.esq)>1);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String ent = sc.nextLine();
        int[] vet;
        //splitar e encher vet
        Pilha p = new Pilha();
        for(int i=0;){////////////////////
            p.push(vet[i]);
        }
        No raiz = new No(p.pop());
        Arvore a = new Arvore(raiz);
        montar(raiz,p);
        sc.close();
    }
}
