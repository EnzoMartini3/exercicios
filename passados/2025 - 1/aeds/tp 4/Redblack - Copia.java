import java.io.*;
import java.util.*;

// Classe Show permanece inalterada, pois é a estrutura de dados.
class Show implements Comparable<Show> {
    private String showId;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private String dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private String[] listedIn;

    // Construtor que inicializa tudo com "NaN"
    public Show() {
        this("NaN", "NaN", "NaN", "NaN", new String[0], "NaN", "NaN", 0, "NaN", "NaN", new String[0]);
    }

    // Construtor rápido
    public Show(String showId, String type, String title, String director, String[] cast, String country, String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = sortArray(cast);
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = sortArray(listedIn);
    }

    // Métodos Get/Set
    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public String[] getCast() { return cast; }
    public void setCast(String[] cast) { this.cast = cast; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String[] getListedIn() { return listedIn; }
    public void setListedIn(String[] listedIn) { this.listedIn = listedIn; }

    // Método clone
    public Show clone() {
        return new Show(showId, type, title, director, cast.clone(), country, dateAdded, releaseYear, rating, duration, listedIn.clone());
    }

    // Método de impressão
    public void imprimir() {
        System.out.print("=> " + showId + " ## " + title + " ## " + type + " ## " + director + " ## ");
        if (cast.length == 0) {
            System.out.print("[NaN] ## ");
        } else {
            System.out.print("[" + String.join(", ", cast) + "] ## ");
        }
        System.out.print(country + " ## " + dateAdded + " ## " + releaseYear + " ## " + rating + " ## " + duration + " ## ");
        if (listedIn.length == 0) {
            System.out.println("[NaN] ##");
        } else {
            System.out.println("[" + String.join(", ", listedIn) + "] ##");
        }
    }

    // Método para ler dados de um arquivo CSV
    public void ler(String targetId, String pathCSV) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(pathCSV));
        String linha = br.readLine(); // Pula o cabeçalho
        while ((linha = br.readLine()) != null) {
            String[] partes = dividirCSV(linha);
            if (partes[0].equals(targetId)) {
                this.showId = partes[0];
                this.type = partes[1];
                this.title = partes[2];
                this.director = partes[3].isEmpty() ? "NaN" : partes[3];
                this.cast = ordenarCampoArray(partes[4]);
                this.country = partes[5].isEmpty() ? "NaN" : partes[5];
                this.dateAdded = partes[6].isEmpty() ? "NaN" : partes[6];
                this.releaseYear = partes[7].isEmpty() ? 0 : Integer.parseInt(partes[7]);
                this.rating = partes[8].isEmpty() ? "NaN" : partes[8];
                this.duration = partes[9].isEmpty() ? "NaN" : partes[9];
                this.listedIn = ordenarCampoArray(partes[10]);
                break;
            }
        }
        br.close();
    }

    // Métodos auxiliares para ordenação e parsing de CSV
    private String[] sortArray(String[] array) {
        if (array == null || array.length == 0) return new String[0];
        Arrays.sort(array);
        return array;
    }

    private String[] ordenarCampoArray(String campo) {
        if (campo == null || campo.trim().isEmpty()) return new String[0];
        String[] partes = campo.split(",\\s*");
        Arrays.sort(partes);
        return partes;
    }

    private String[] dividirCSV(String linha) {
        List<String> partes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean dentroAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '\"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                partes.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        partes.add(sb.toString());
        return partes.toArray(new String[0]);
    }

    @Override
    public int compareTo(Show outroShow) {
        // A comparação é feita pelo título (name)
        return this.title.compareTo(outroShow.title);
    }
}

// Nó da Árvore Rubro-Negra
class No {
    Show show;
    No esq, dir;
    boolean cor; // false: VERMELHO, true: PRETO

    No(Show show) {
        this(show, false); // Novos nós são inicialmente VERMELHOS
    }

    No(Show show, boolean cor) {
        this.show = show;
        this.esq = null;
        this.dir = null;
        this.cor = cor;
    }
}

// Classe Redblack (Árvore Rubro-Negra)
public class Redblack {
    private No raiz;
    private long comparacoes; // Contador de comparações

    // Constantes para as cores (seguindo a convenção do usuário)
    private final boolean PRETO = true; // true é preto
    private final boolean VERMELHO = false; // false é branco (vermelho)

    public Redblack() {
        this.raiz = null;
        this.comparacoes = 0;
    }

    /**
     * Verifica se o nó não é nulo.
     * @param no O nó a ser verificado.
     * @return true se o nó não for nulo, false caso contrário.
     */
    private boolean ehNo(No no) {
        return no != null;
    }

    /**
     * Verifica se a cor de um nó é VERMELHO.
     * Nós nulos são considerados PRETO por convenção da Árvore Rubro-Negra.
     * @param no O nó a ser verificado.
     * @return true se o nó é VERMELHO, false caso contrário.
     */
    private boolean ehVermelho(No no) {
        return ehNo(no) && no.cor == VERMELHO;
    }

    /**
     * Realiza uma rotação à esquerda na subárvore enraizada em 'no'.
     * Usada para rebalancear a árvore após inserções.
     * @param no O nó em que a rotação será realizada.
     * @return O novo nó raiz da subárvore após a rotação.
     */
    private No rotacionarEsquerda(No no) {
        // 'no' é o nó desbalanceado, 'dir' é o nó que irá para a posição de 'no'
        No dir = no.dir;
        no.dir = dir.esq; // A subárvore esquerda de 'dir' se torna a subárvore direita de 'no'
        dir.esq = no; // 'no' se torna o filho esquerdo de 'dir'

        // Troca as cores: a nova raiz 'dir' herda a cor de 'no', e 'no' se torna VERMELHO
        dir.cor = no.cor;
        no.cor = VERMELHO;
        return dir; // Retorna a nova raiz da subárvore
    }

    /**
     * Realiza uma rotação à direita na subárvore enraizada em 'no'.
     * Usada para rebalancear a árvore após inserções.
     * @param no O nó em que a rotação será realizada.
     * @return O novo nó raiz da subárvore após a rotação.
     */
    private No rotacionarDireita(No no) {
        // 'no' é o nó desbalanceado, 'esq' é o nó que irá para a posição de 'no'
        No esq = no.esq;
        no.esq = esq.dir; // A subárvore direita de 'esq' se torna a subárvore esquerda de 'no'
        esq.dir = no; // 'no' se torna o filho direito de 'esq'

        // Troca as cores: a nova raiz 'esq' herda a cor de 'no', e 'no' se torna VERMELHO
        esq.cor = no.cor;
        no.cor = VERMELHO;
        return esq; // Retorna a nova raiz da subárvore
    }

    /**
     * Troca as cores de um nó e de seus filhos.
     * Utilizada no processo de balanceamento ("color flip").
     * @param no O nó cujas cores e de seus filhos serão trocadas.
     */
    private void trocarCor(No no) {
        no.cor = !no.cor; // Inverte a cor do nó
        no.esq.cor = !no.esq.cor; // Inverte a cor do filho esquerdo
        no.dir.cor = !no.dir.cor; // Inverte a cor do filho direito
    }

    /**
     * Insere um objeto Show na árvore Rubro-Negra.
     * O elemento é inserido e a árvore é rebalanceada para manter suas propriedades.
     * @param s O objeto Show a ser inserido.
     */
    public void inserir(Show s) {
        raiz = inserir(s, raiz);
        raiz.cor = PRETO; // A raiz deve ser sempre PRETA, garantindo a propriedade 2
    }

    /**
     * Método auxiliar recursivo para a inserção e balanceamento da árvore Rubro-Negra.
     * Este método implementa as regras de balanceamento da árvore Rubro-Negra.
     * @param s O objeto Show a ser inserido.
     * @param no O nó atual na recursão.
     * @return O nó (potencialmente modificado após rotações e trocas de cor) para o nível superior.
     */
    private No inserir(Show s, No no) {
        // Caso base da recursão: se o nó é nulo, cria um novo nó VERMELHO e o retorna.
        if (no == null) {
            return new No(s); // Novo nó é VERMELHO (false)
        }

        // Realiza a inserção de forma recursiva, como em uma Árvore de Busca Binária comum.
        int cmp = s.compareTo(no.show);
        if (cmp < 0) { // Se o Show a ser inserido é "menor", vai para a subárvore esquerda
            no.esq = inserir(s, no.esq);
        } else if (cmp > 0) { // Se o Show a ser inserido é "maior", vai para a subárvore direita
            no.dir = inserir(s, no.dir);
        } else {
            // Caso o item já exista na árvore (título duplicado), não faz nada.
            // Para este exercício, assumimos que duplicatas não são inseridas.
            return no;
        }

        // --- Aplica as regras de balanceamento da Árvore Rubro-Negra após a recursão retornar ---

        // Regra 1: Se o filho direito é VERMELHO e o filho esquerdo é PRETO (ou nulo).
        // Isso indica um desbalanceamento à direita, requerendo uma rotação à esquerda.
        if (ehVermelho(no.dir) && !ehVermelho(no.esq)) {
            no = rotacionarEsquerda(no);
        }

        // Regra 2: Se o filho esquerdo é VERMELHO e o neto esquerdo (filho esquerdo do filho esquerdo) também é VERMELHO.
        // Isso indica um desbalanceamento à esquerda, requerendo uma rotação à direita.
        if (ehVermelho(no.esq) && ehVermelho(no.esq.esq)) {
            no = rotacionarDireita(no);
        }

        // Regra 3: Se ambos os filhos esquerdo e direito são VERMELHOS.
        // Isso indica que o nó atual precisa ser "colorido" (color flip) para manter a altura negra.
        if (ehVermelho(no.esq) && ehVermelho(no.dir)) {
            trocarCor(no);
        }

        return no; // Retorna o nó (potencialmente modificado) para o nível superior da recursão.
    }

    /**
     * Pesquisa um objeto Show na árvore Rubro-Negra e imprime o caminho percorrido
     * até encontrar (ou não) o elemento.
     * @param s O objeto Show a ser pesquisado (apenas o atributo 'title' é usado para comparação).
     * @return true se o objeto for encontrado na árvore, false caso contrário.
     */
    public boolean pesquisar(Show s) {
        StringBuilder path = new StringBuilder("=>raiz");
        boolean encontrado = pesquisar(s, raiz, path);
        System.out.println(path.toString() + (encontrado ? " SIM" : " NAO"));
        return encontrado;
    }

    /**
     * Método auxiliar recursivo para a pesquisa na árvore.
     * @param s O objeto Show a ser pesquisado.
     * @param no O nó atual na recursão.
     * @param path O StringBuilder para construir o caminho de navegação.
     * @return true se o objeto for encontrado, false caso contrário.
     */
    private boolean pesquisar(Show s, No no, StringBuilder path) {
        if (no == null) {
            return false; // Nó nulo, elemento não encontrado neste caminho.
        }
        comparacoes++; // Incrementa o contador de comparações para cada nó visitado.
        int cmp = s.compareTo(no.show);

        if (cmp == 0) { // Elemento encontrado (títulos são iguais)
            return true;
        } else if (cmp < 0) { // O título a ser pesquisado é menor, vai para a subárvore esquerda
            path.append(" esq");
            return pesquisar(s, no.esq, path);
        } else { // O título a ser pesquisado é maior, vai para a subárvore direita
            path.append(" dir");
            return pesquisar(s, no.dir, path);
        }
    }

    /**
     * Retorna o número total de comparações realizadas durante as operações de pesquisa.
     * @return O número de comparações.
     */
    public long getComparacoes() {
        return comparacoes;
    }

    /**
     * Reseta o contador de comparações para zero.
     */
    public void resetComparacoes() {
        this.comparacoes = 0;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Redblack arv = new Redblack(); // Instancia a árvore Rubro-Negra
        String pathCSV = "/tmp/disneyplus.csv"; // Caminho do arquivo CSV

        String linha;
        long startTime = System.nanoTime(); // Início da contagem de tempo para a operação completa

        // --- Fase de Inserção ---
        // Lê IDs de shows do console até encontrar "FIM", cria objetos Show e os insere na árvore.
        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show s = new Show();
            s.ler(linha, pathCSV); // Carrega os dados do Show a partir do CSV
            arv.inserir(s); // Insere o Show na árvore Rubro-Negra
        }

        // --- Fase de Pesquisa ---
        // Lê títulos de shows do console até encontrar "FIM" e pesquisa-os na árvore.
        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show sPesquisa = new Show();
            sPesquisa.setTitle(linha); // Apenas o título é necessário para a pesquisa
            arv.pesquisar(sPesquisa); // Realiza a pesquisa e imprime o caminho
        }

        long endTime = System.nanoTime(); // Fim da contagem de tempo
        long duration = (endTime - startTime) / 1_000_000; // Duração total em milissegundos

        // --- Geração do arquivo de log ---
        String matricula = "850602"; // Substitua pela sua matrícula
        try (FileWriter fw = new FileWriter(matricula + "_arvoreRedBlack.txt"); // Nome do arquivo de log alterado
             BufferedWriter bw = new BufferedWriter(fw)) {
            // Escreve a matrícula, duração da execução e total de comparações no arquivo.
            bw.write(matricula + "\t" + duration + "\t" + arv.getComparacoes());
        } catch (IOException e) {
            // Em caso de erro na escrita do arquivo, imprime uma mensagem de erro no console.
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }

        sc.close(); // Fecha o Scanner para liberar recursos.
    }
}
