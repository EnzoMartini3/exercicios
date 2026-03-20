import java.io.*;
import java.util.*;

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

    // Construtor que inicia tudo com NaN
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

    // Getters e Setters
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

    // Clone
    public Show clone() {
        return new Show(showId, type, title, director, cast.clone(), country, dateAdded, releaseYear, rating, duration, listedIn.clone());
    }

    // Impressão
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

    // Leitura do CSV
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
        // A comparação deve ser pelo título (name)
        return this.title.compareTo(outroShow.title);
    }

    /**
     * Calcula a soma dos valores ASCII dos caracteres do título.
     * Usado para a função hash.
     * @return A soma dos valores ASCII do título.
     */
    public int getAsciiSumOfTitle() {
        int sum = 0;
        if (this.title != null) {
            for (char c : this.title.toCharArray()) {
                sum += (int) c;
            }
        }
        return sum;
    }
}

class HashTableChaining {
    private ArrayList<Show>[] table; // Tabela de arrays de listas para encadeamento
    private int tamTab;              // Tamanho da tabela principal (21)
    private long comparacoes;        // Contador de comparações
    private int numElements;         // Contador de elementos inseridos

    /**
     * Construtor da Tabela Hash com Encadeamento Separado.
     * @param tamTab Tamanho da tabela principal.
     */
    @SuppressWarnings("unchecked") // Para suprimir o aviso de unchecked cast ao criar o array genérico
    public HashTableChaining(int tamTab) {
        this.tamTab = tamTab;
        // Cria um array de ArrayLists. Em Java, você não pode criar um array de tipos genéricos diretamente,
        // então é comum criar um array de Object e depois fazer um cast.
        this.table = (ArrayList<Show>[]) new ArrayList[tamTab];
        this.comparacoes = 0;
        this.numElements = 0;
    }

    /**
     * Função hash: (ASCII title) mod tamTab.
     * @param title O título do show.
     * @return O índice hash na tabela principal.
     */
    public int hash(String title) {
        int sum = 0;
        if (title != null) {
            for (char c : title.toCharArray()) {
                sum += (int) c;
            }
        }
        return sum % tamTab;
    }

    /**
     * Insere um objeto Show na tabela hash usando encadeamento.
     * @param show O objeto Show a ser inserido.
     */
    public void inserir(Show show) {
        int index = hash(show.getTitle());
        comparacoes++; // Uma comparação para acessar o slot principal

        // Se a lista naquele índice estiver vazia, cria uma nova
        if (table[index] == null) {
            table[index] = new ArrayList<>();
        }
        table[index].add(show);
        numElements++; // Incrementa o contador de elementos
    }

    /**
     * Pesquisa um título na tabela hash e nas listas encadeadas.
     * @param title O título a ser pesquisado.
     * @return A posição do elemento (índice da tabela principal), ou -1 se não encontrado.
     */
    public int pesquisar(String title) {
        int index = hash(title);
        comparacoes++; // Uma comparação para acessar o slot principal

        // Se o slot estiver vazio, o elemento não está na tabela
        if (table[index] == null) {
            return -1;
        }

        // Procura o elemento na lista encadeada (ArrayList)
        for (Show s : table[index]) {
            comparacoes++; // Uma comparação para cada elemento na lista
            if (s.getTitle().equals(title)) {
                return index; // Retorna o índice da tabela principal onde o elemento foi encontrado
            }
        }

        return -1; // Não encontrado na lista
    }

    /**
     * Retorna o número total de comparações realizadas.
     * @return O número de comparações.
     */
    public long getComparacoes() {
        return comparacoes;
    }

    /**
     * Reseta o contador de comparações.
     */
    public void resetComparacoes() {
        this.comparacoes = 0;
    }
}

public class HashLista { // Renomeado o arquivo principal
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // Tamanho da tabela principal (tamTab) é 21. A "área de reserva" é flexível
        // via ArrayLists, então tamReserva não é mais um parâmetro fixo.
        int TAM_TABELA = 21;
        HashTableChaining hashTable = new HashTableChaining(TAM_TABELA);
        String pathCSV = "/tmp/disneyplus.csv"; // Caminho do arquivo CSV
        String linha;
        long startTime = System.nanoTime(); // Início da contagem de tempo

        // Parte 1: Leitura dos IDs e inserção na tabela hash
        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show s = new Show();
            s.ler(linha, pathCSV);
            hashTable.inserir(s);
        }

        // Parte 2: Pesquisa dos títulos na tabela hash
        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show sPesquisa = new Show();
            sPesquisa.setTitle(linha);

            int posicao = hashTable.pesquisar(sPesquisa.getTitle());

            // Imprime a saída no formato especificado, com um espaço antes do parêntese
            if (posicao != -1) {
                System.out.println(" (Posicao: " + posicao + ") SIM");
            } else {
                // Para "NÃO", mostramos a posição hash inicial.
                int hashInicial = hashTable.hash(sPesquisa.getTitle());
                System.out.println(" (Posicao: " + hashInicial + ") NAO");
            }
        }

        long endTime = System.nanoTime(); // Fim da contagem de tempo
        long duration = (endTime - startTime) / 1_000_000; // Duração em milissegundos

        // Geração do arquivo de log
        String matricula = "850602"; // Substitua pela sua matrícula
        try (FileWriter fw = new FileWriter(matricula + "matrícula_hashIndireta.txt"); // Nome do log alterado
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(matricula + "\t" + duration + "\t" + hashTable.getComparacoes());
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }

        sc.close(); // Fecha o scanner
    }
}