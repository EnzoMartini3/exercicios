class No{
    boolean cor;
    int elemento;
    No esq, dir;

    No(){
        this(-1);
    }

    No(int e){
        this(e, false, null, null);
    }

    No(int e, boolean cor){
        this(e, cor, null, null);
    }
}

public class Redblack {
   private No raiz; // Raiz da arvore.

   public Redblack() {
      raiz = null;
   }

    boolean isNoTipo4(No i){
        return (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true);
    }

   public boolean pesquisar(int elemento) {
      return pesquisar(elemento, raiz);
   }

   private boolean pesquisar(int elemento, No i) {
      boolean resp;
      if (i == null) {
         resp = false;
      } else if (elemento == i.elemento) {
         resp = true;
      } else if (elemento < i.elemento) {
         resp = pesquisar(elemento, i.esq);
      } else {
         resp = pesquisar(elemento, i.dir);
      }
      return resp;
   }

   public void caminharCentral() {
      System.out.print("[ ");
      caminharCentral(raiz);
      System.out.println("]");
   }
   private void caminharCentral(No i) {
      if (i != null) {
         caminharCentral(i.esq); // Elementos da esquerda.
         System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
         caminharCentral(i.dir); // Elementos da direita.
      }
   }

   /**
    * Metodo publico iterativo para exibir elementos.
    */
   public void caminharPre() {
      System.out.print("[ ");
      caminharPre(raiz);
      System.out.println("]");
   }
   private void caminharPre(No i) {
      if (i != null) {
         System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
         caminharPre(i.esq); // Elementos da esquerda.
         caminharPre(i.dir); // Elementos da direita.
      }
   }
   /**
    * Metodo publico iterativo para exibir elementos.
    */
   public void caminharPos() {
      System.out.print("[ ");
      caminharPos(raiz);
      System.out.println("]");
   }
   private void caminharPos(No i) {
      if (i != null) {
         caminharPos(i.esq); // Elementos da esquerda.
         caminharPos(i.dir); // Elementos da direita.
         System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); // Conteudo do no.
      }
   }

   public void inserir(int elemento){
      if (raiz == null) {
         raiz = new No(elemento);
      } else if (raiz.esq == null && raiz.dir == null) {
         if (elemento < raiz.elemento) {
               raiz.esq = new No(elemento);
         } else {
               raiz.dir = new No(elemento);
         }

      } else if (raiz.esq == null) {
         if (elemento < raiz.elemento) {
               raiz.esq = new No(elemento);

         } else if (elemento < raiz.dir.elemento) {
               raiz.esq = new No(raiz.elemento);
               raiz.elemento = elemento;
         } else {
               raiz.esq = new No(raiz.elemento);
               raiz.elemento = raiz.dir.elemento;
               raiz.dir.elemento = elemento;
         }
         raiz.esq.cor = raiz.dir.cor = false;

      } else if (raiz.dir == null) {
         if (elemento > raiz.elemento) {
               raiz.dir = new No(elemento);
         } else if (elemento > raiz.esq.elemento) {
               raiz.dir = new No(raiz.elemento);
               raiz.elemento = elemento;
         } else {
               raiz.dir = new No(raiz.elemento);
               raiz.elemento = raiz.esq.elemento;
               raiz.esq.elemento = elemento;
         }
         raiz.esq.cor = raiz.dir.cor = false;
      } else {
         inserir(elemento, null, null, null, raiz);
      }
      raiz.cor = false;
   }

   private void balancear(No bisavo, No avo, No pai, No i) {
      // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
      if (pai.cor == true) {
         // 4 tipos de reequilibrios e acoplamento
         if (pai.elemento > avo.elemento) { // rotacao a esquerda ou direita-esquerda
            if (i.elemento > pai.elemento) {
               avo = rotacaoEsq(avo);
            } else {
               avo = rotacaoDirEsq(avo);
            }
         } else { // rotacao a direita ou esquerda-direita
            if (i.elemento < pai.elemento) {
               avo = rotacaoDir(avo);
            } else {
               avo = rotacaoEsqDir(avo);
            }
         }
         if (bisavo == null) {
            raiz = avo;
         } else if (avo.elemento < bisavo.elemento) {
            bisavo.esq = avo;
         } else {
            bisavo.dir = avo;
         }
         // reestabelecer as cores apos a rotacao
         avo.cor = false;
         avo.esq.cor = avo.dir.cor = true;
      } // if(pai.cor == true)
   }


   private void inserir(int elemento, No bisavo, No avo, No pai, No i) throws Exception {
      if (i == null) {
         if (elemento < pai.elemento) {
            i = pai.esq = new No(elemento, true);
         } else {
            i = pai.dir = new No(elemento, true);
         }
         if (pai.cor == true) {
            balancear(bisavo, avo, pai, i);
         }
      } else {
         // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
         if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
            i.cor = true;
            i.esq.cor = i.dir.cor = false;
            if (i == raiz) {
               i.cor = false;
            } else if (pai.cor == true) {
               balancear(bisavo, avo, pai, i);
            }
         }
         if (elemento < i.elemento) {
            inserir(elemento, avo, pai, i, i.esq);
         } else if (elemento > i.elemento) {
            inserir(elemento, avo, pai, i, i.dir);
         } else {
            throw new Exception("Erro inserir (elemento repetido)!");
         }
      }
   }

   private No rotacaoDir(No no) {
      No noEsq = no.esq;
      No noEsqDir = noEsq.dir;

      noEsq.dir = no;
      no.esq = noEsqDir;

      return noEsq;
   }

   private No rotacaoEsq(No no) {
      No noDir = no.dir;
      No noDirEsq = noDir.esq;

      noDir.esq = no;
      no.dir = noDirEsq;
      return noDir;
   }

   private No rotacaoDirEsq(No no) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
   }

   private No rotacaoEsqDir(No no) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
   }
}