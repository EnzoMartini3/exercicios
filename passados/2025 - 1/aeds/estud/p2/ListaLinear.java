class ListaLinear {
    private int[] lista;
    private int tamanho;
    private int capacidade;

    // Construtor
    public ListaLinear(int max) {
        this.capacidade = max;
        this.lista = new int[max];
        this.tamanho = 0;
    }

    // Insere no início da lista
    public void inserirInicio(int elem) {
        if (tamanho == capacidade) {
            throw new RuntimeException("Overflow: Lista cheia");
        }
        for (int i = tamanho; i > 0; i--) {
            lista[i] = lista[i - 1];
        }
        lista[0] = elem;
        tamanho++;
    }

    // Insere no fim da lista
    public void inserirFim(int elem) {
        if (tamanho == capacidade) {
            throw new RuntimeException("Overflow: Lista cheia");
        }
        lista[tamanho++] = elem;
    }

    // Insere em uma posição específica
    public void inserir(int elem, int pos) {
        if (tamanho == capacidade) {
            throw new RuntimeException("Overflow: Lista cheia");
        }
        if (pos < 0 || pos > tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        for (int i = tamanho; i > pos; i--) {
            lista[i] = lista[i - 1];
        }
        lista[pos] = elem;
        tamanho++;
    }

    // Remove do início da lista
    public int removerInicio() {
        if (tamanho == 0) {
            throw new RuntimeException("Underflow: Lista vazia");
        }
        int removido = lista[0];
        for (int i = 0; i < tamanho - 1; i++) {
            lista[i] = lista[i + 1];
        }
        tamanho--;
        return removido;
    }

    // Remove do fim da lista
    public int removerFim() {
        if (tamanho == 0) {
            throw new RuntimeException("Underflow: Lista vazia");
        }
        return lista[--tamanho];
    }

    // Remove de uma posição específica
    public int remover(int pos) {
        if (tamanho == 0) {
            throw new RuntimeException("Underflow: Lista vazia");
        }
        if (pos < 0 || pos >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        int removido = lista[pos];
        for (int i = pos; i < tamanho - 1; i++) {
            lista[i] = lista[i + 1];
        }
        tamanho--;
        return removido;
    }

    // Mostra os elementos da lista
    public void mostrar() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print(lista[i] + " ");
        }
        System.out.println();
    }

    // Pesquisa um elemento na lista
    public boolean pesquisar(int elem) {
        for (int i = 0; i < tamanho; i++) {
            if (lista[i] == elem) {
                return true;
            }
        }
        return false;
    }
}

// Programa de teste
public class Main {
    public static void main(String[] args) {
        ListaLinear lista = new ListaLinear(5);
        
        lista.inserirFim(10);
        lista.inserirFim(20);
        lista.inserirInicio(5);
        lista.inserir(15, 2);
        lista.mostrar(); // Saída esperada: 5 10 15 20

        lista.removerInicio();
        lista.mostrar(); // Saída esperada: 10 15 20

        lista.removerFim();
        lista.mostrar(); // Saída esperada: 10 15

        lista.remover(1);
        lista.mostrar(); // Saída esperada: 10

        System.out.println("Pesquisando 10: " + lista.pesquisar(10)); // true
        System.out.println("Pesquisando 20: " + lista.pesquisar(20)); // false
    }
}
