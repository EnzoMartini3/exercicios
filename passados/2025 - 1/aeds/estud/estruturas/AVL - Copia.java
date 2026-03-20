// cada no conhece seu fator de balanceamento.
// quando o fator de balanceamento de uma avl se torna +/-2, uma rotacao acontece.
// se 2 for positivo, executa uma RE ou RDE.
// se 2 for negativo, executa uma RD ou RED.
// se o fator do filho a direita for -1, fazemos uma RDE.
// se o fator do filho a esquerda for 1, fazemos uma RED.

public class AVL {
	private No raiz;

	public AVL() {
		raiz = null;
	}

	public boolean pesquisar(int x) {
		return pesquisar(x, raiz);
	}
	private boolean pesquisar(int x, No i) {
		boolean resp;
		if (i == null) {
			resp = false;
		} else if (x == i.elemento) {
			resp = true;
		} else if (x < i.elemento) {
			resp = pesquisar(x, i.esq);
		} else {
			resp = pesquisar(x, i.dir);
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
			System.out.print(i.elemento + " "); // Conteudo do no.
			caminharCentral(i.dir); // Elementos da direita.
		}
	}

	public void caminharPre() {
		System.out.print("[ ");
		caminharPre(raiz);
		System.out.println("]");
	}

	private void caminharPre(No i) {
		if (i != null) {
			System.out.print(i.elemento + "(fator " + (No.getNivel(i.dir) - No.getNivel(i.esq)) + ") "); // Conteudo do no.
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
			System.out.print(i.elemento + " "); // Conteudo do no.
		}
	}


	public void inserir(int x) throws Exception {
		raiz = inserir(x, raiz);
	}
	private No inserir(int x, No i) throws Exception {
		if (i == null) {
			i = new No(x);
		} else if (x < i.elemento) {
			i.esq = inserir(x, i.esq);
		} else if (x > i.elemento) {
			i.dir = inserir(x, i.dir);
		} else {
			throw new Exception("Erro ao inserir!");
		}
		return balancear(i);
	}

	public void remover(int x) throws Exception {
		raiz = remover(x, raiz);
	}
	private No remover(int x, No i) throws Exception {
		if (i == null) {
			throw new Exception("Erro ao remover!");
		} else if (x < i.elemento) {
			i.esq = remover(x, i.esq);
		} else if (x > i.elemento) {
			i.dir = remover(x, i.dir);
		} else if (i.dir == null) {
			i = i.esq;
		} else if (i.esq == null) {
			i = i.dir;
		} else {
			i.esq = maiorEsq(i, i.esq);
		}
		return balancear(i);
	}

	private No maiorEsq(No i, No j) {
		if (j.dir == null) {
			i.elemento = j.elemento; // Substitui i por j.
			j = j.esq; // Substitui j por j.ESQ.
		} else {
			j.dir = maiorEsq(i, j.dir);
		}
		return j;
	}

	private No balancear(No no) throws Exception {
		if (no != null) {
			int fator = No.getNivel(no.dir) - No.getNivel(no.esq);

			if (Math.abs(fator) <= 1) { // Se balanceada
				no.setNivel();
			} else if (fator == 2) { // Se desbalanceada para a direita
				int fatorFilhoDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);

				if (fatorFilhoDir == -1) { // Se o filho a direita tambem estiver desbalanceado
					no.dir = rotacionarDir(no.dir);
				}
				no = rotacionarEsq(no);			
			} else if (fator == -2) { // Se desbalanceada para a esquerda
				int fatorFilhoEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);
				if (fatorFilhoEsq == 1) { // Se o filho a esquerda tambem estiver desbalanceado
					no.esq = rotacionarEsq(no.esq);
				}
				no = rotacionarDir(no);
			}
		}
		return no;
	}

	private No rotacionarDir(No no) {
		No noEsq = no.esq;
		No noEsqDir = noEsq.dir;
		noEsq.dir = no;
		no.esq = noEsqDir;

		no.setNivel(); // Atualizar o nivel do no
		noEsq.setNivel(); // Atualizar o nivel do noEsq

		return noEsq;
	}

	private No rotacionarEsq(No no) {
		No noDir = no.dir;
		No noDirEsq = noDir.esq;
		noDir.esq = no;
		no.dir = noDirEsq;

		no.setNivel(); // Atualizar o nivel do no
		noDir.setNivel(); // Atualizar o nivel do noDir
		return noDir;
	}
}

class No {
	public int elemento; // Conteudo do no.
	public No esq, dir; // Filhos da esq e dir.
	public int nivel; // Numero de niveis abaixo do no

	public No(int elemento) {
		this(elemento, null, null, 1);
	}

	public No(int elemento, No esq, No dir, int nivel) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
		this.nivel = nivel;
	}

	public void setNivel(){
		this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
	}

	public static int getNivel(No no) {
		return (no == null) ? 0 : no.nivel;
	}
}

public class Principal {
	public static void main(String[] args) {
		try {
			AVL avl = new AVL();
			//int array[] = {4,35,10,13,3,30,15,12,7,40,20};
			int array[] = {1,2,3,4,5,6,7,8,9,10};
			for(int item: array){
				System.out.println("Inserindo -> " + item);
				avl.inserir(item);
				avl.caminharPre();
			}
		} catch (Exception erro) {
			System.out.println(erro.getMessage());
		}
	}
}