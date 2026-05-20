/*Complete o código da classe Matriz dinâmica visto na sala de aula. A primeira tarefa consiste em, no construtor da classe Matriz, dados os números de linha e coluna, fazer as devidas alocações de células. As demais tarefas são as implementações dos métodos:

Matriz soma(Matriz)
Matriz multiplicacao(Matriz)
void mostrarDiagonalPrincipal()
void mostrarDiagonalSecundaria()

.A entrada padrão é composta por vários casos de teste, sendo que o número de casos é um inteiro contido na primeira linha da entrada. Em seguida, temos cada um dos casos de teste. Cada caso é composto por duas matrizes.

.Para cada caso de teste:
As duas primeiras linhas contêm um número inteiro cada, representando os números de linhas (l) e de colunas (c) da primeira matriz, respectivamente.
Em seguida, temos os elementos da primeira matriz, que estão representados nas próximas l linhas, cada uma com c colunas.
Nas duas linhas seguintes, temos os números de linhas (l2) e colunas (c2) da segunda matriz do caso de teste.
As l2 linhas seguintes têm c2 colunas contendo os elementos da segunda matriz.

.A saída padrão contém várias linhas para cada caso de teste:
As duas primeiras linhas de saída de um caso de teste correspondem às diagonais principal e secundária da primeira matriz, respectivamente.
As demais ls linhas de um caso de teste correspondem às linhas da matriz obtida pela soma das duas matrizes do caso de teste (cada linha contendo cs colunas).
As linhas seguintes do caso de teste contêm lm linhas com cm colunas representando os elementos da matriz de multiplicação.

Onde:
ls e cs são os números de linhas e colunas da matriz de soma
lm e cm são os números de linhas e colunas da matriz de multiplicação */

import java.io.*;
import java.util.*;

class Celula{
    int elemento;
    Celula dir, esq, inf, sup;

    Celula(int elem){
        this.elemento = elem;
        dir = esq = inf = sup = null;
    }
}

public class Matriz{
    private Celula inicio;
    private int li,col;

    Matriz(int l, int c){
        this.li = l;
        this.col = c;
        iniciar();
    }

    public void iniciar(){
        inicio = new Celula(0);
        Celula atual = inicio;

        for (int j = 1; j < col; j++) {
            atual.dir = new Celula(0);
            atual.dir.esq = atual;
            atual = atual.dir;
        }

        // Construir linhas restantes
        Celula linhaAcima = inicio;
        for (int i = 1; i < li; i++) {
            Celula novaLinha = new Celula(0);
            novaLinha.sup = linhaAcima;
            linhaAcima.inf = novaLinha;
            Celula atualLinha = novaLinha;
            Celula acima = linhaAcima.dir;

            for (int j = 1; j < col; j++) {
                atualLinha.dir = new Celula(0);
                atualLinha.dir.esq = atualLinha;
                atualLinha = atualLinha.dir;
                atualLinha.sup = acima;
                acima.inf = atualLinha;
                acima = acima.dir;
            }
            linhaAcima = linhaAcima.inf;
        }
    }

    public void mxEncher(Scanner sc) {
        Celula linha = inicio;
        for (int i = 0; i < li; i++) {
            Celula coluna = linha;
            for (int j = 0; j < col; j++) {
                coluna.elemento = sc.nextInt();
                coluna = coluna.dir;
            }
            linha = linha.inf;
        }
    }

    Matriz soma(Matriz m2) {
        Matriz soma = new Matriz(this.li, this.col);
        Celula l1 = this.inicio;
        Celula l2 = m2.inicio;
        Celula ls = soma.inicio;
        for (int i = 0; i < li; i++) {
            Celula c1 = l1;
            Celula c2 = l2;
            Celula cRes = ls;
            for (int j = 0; j < col; j++) {
                cRes.elemento = c1.elemento + c2.elemento;
                c1 = c1.dir;
                c2 = c2.dir;
                cRes = cRes.dir;
            }
            l1 = l1.inf;
            l2 = l2.inf;
            ls = ls.inf;
        }

    return soma;
}

    Matriz multi(Matriz m2) {
        Matriz resultado = new Matriz(this.li, m2.col);
        Celula linhaThis = this.inicio;
        Celula linhaRes = resultado.inicio;
        for (int i = 0; i < this.li; i++) {
            Celula colunaOutra = m2.inicio;
            Celula celulaRes = linhaRes;

            for (int j = 0; j < m2.col; j++) {
                int soma = 0;
                Celula celulaA = linhaThis;
                Celula celulaB = colunaOutra;

                for (int k = 0; k < this.col; k++) {
                    soma += celulaA.elemento * celulaB.elemento;
                    celulaA = celulaA.dir;
                    celulaB = celulaB.inf;
                }
                celulaRes.elemento = soma;
                celulaRes = celulaRes.dir;
                colunaOutra = colunaOutra.dir;
            }
            linhaThis = linhaThis.inf;
            linhaRes = linhaRes.inf;
    }

    return resultado;
}
 void mostrarDiagonalPrincipal() {
        Celula atual = inicio;
        for (int i = 0; i < Math.min(li, col); i++) {
            System.out.print(atual.elemento + " ");
            if (atual.inf != null && atual.dir != null)
                atual = atual.inf.dir;
        }
        System.out.println();
    }

    void mostrarDiagonalSecundaria() {
        Celula atual = inicio;
        for (int i = 1; i < col; i++) atual = atual.dir;

        for (int i = 0; i < Math.min(li, col); i++) {
            System.out.print(atual.elemento + " ");
            if (atual.inf != null && atual.esq != null)
                atual = atual.inf.esq;
        }
        System.out.println();
    }
    
    void mostrar() {
        Celula linha = inicio;
        for (int i = 0; i < li; i++) {
            Celula coluna = linha;
            for (int j = 0; j < col; j++) {
                System.out.print(coluna.elemento + " ");
                coluna = coluna.dir;
            }
            System.out.println();
            linha = linha.inf;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casos = sc.nextInt();
        for (int i = 0; i < casos; i++) {
            int l1 = sc.nextInt();
            int c1 = sc.nextInt();
            Matriz m1 = new Matriz(l1, c1);
            m1.mxEncher(sc);

            int l2 = sc.nextInt();
            int c2 = sc.nextInt();
            Matriz m2 = new Matriz(l2, c2);
            m2.mxEncher(sc);

            m1.mostrarDiagonalPrincipal();
            m1.mostrarDiagonalSecundaria();

            Matriz soma = m1.soma(m2);
            soma.mostrar();

            Matriz multi = m1.multi(m2);
            multi.mostrar();
        }

        sc.close();
    }
}