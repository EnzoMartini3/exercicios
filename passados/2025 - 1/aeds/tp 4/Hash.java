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

class HashTable {
    private Show[] table;          // Tabela principal
    private Show[] reserveArea;    // Área de reserva
    private int tamTab;            // Tamanho da tabela principal (21)
    private int tamReserva;        // Tamanho da área de reserva (9)
    private int nextReserveIndex;  // Próximo índice disponível na área de reserva
    private long comparacoes;      // Contador de comparações
    private int numElements;       // NOVO: Contador de elementos inseridos

    /**
     * Construtor da Tabela Hash com Área de Reserva.
     * @param tamTab Tamanho da tabela principal.
     * @param tamReserva Tamanho da área de reserva.
     */
    public HashTable(int tamTab, int tamReserva) {
        this.tamTab = tamTab;
        this.tamReserva = tamReserva;
        this.table = new Show[tamTab];
        this.reserveArea = new Show[tamReserva];
        this.nextReserveIndex = 0;
        this.comparacoes = 0;
        this.numElements = 0; // Inicializa o contador de elementos
    }

    /**
     * Função hash: (ASCII title) mod tamTab.
     * Alterada para public para acesso do main.
     * @param title O título do show.
     * @return O índice hash na tabela principal.
     */
    public int hash(String title) { // Tornada public
        int sum = 0;
        if (title != null) {
            for (char c : title.toCharArray()) {
                sum += (int) c;
            }
        }
        return sum % tamTab;
    }

    /**
     * Insere um objeto Show na tabela hash.
     * Se houver colisão na tabela principal, tenta inserir na área de reserva.
     * Retorna true se a inserção foi bem-sucedida, false caso contrário (tabela cheia).
     * @param show O objeto Show a ser inserido.
     * @return true se a inserção ocorreu, false se a tabela está cheia.
     */
    public boolean inserir(Show show) {
        // Verifica se a tabela hash está completamente cheia (principal + reserva)
        if (numElements >= (tamTab + tamReserva)) {
            // System.err.println("AVISO: Tabela hash (principal + reserva) cheia. Não foi possível inserir: " + show.getTitle());
            return false; // Não há espaço para inserir
        }

        int index = hash(show.getTitle());
        comparacoes++; // Uma comparação para verificar o slot principal

        // Tenta inserir na posição calculada pela hash
        if (table[index] == null) {
            table[index] = show;
            numElements++; // Incrementa o contador de elementos
            return true;
        } else {
            // Colisão, tenta inserir na área de reserva
            if (nextReserveIndex < tamReserva) {
                reserveArea[nextReserveIndex] = show;
                nextReserveIndex++;
                numElements++; // Incrementa o contador de elementos
                return true;
            } else {
                // Isso só deve acontecer se 'numElements' não pegou antes, o que é improvável com a verificação inicial.
                // System.err.println("AVISO: Área de reserva cheia para colisão. Inserção de " + show.getTitle() + " falhou.");
                return false; // Área de reserva cheia para este elemento
            }
        }
    }

    /**
     * Pesquisa um título na tabela hash e na área de reserva.
     * @param title O título a ser pesquisado.
     * @return A posição do elemento (0 a tamTab-1 para tabela principal, tamTab a tamTotal-1 para reserva),
     * ou -1 se o elemento não for encontrado.
     */
    public int pesquisar(String title) {
        int index = hash(title);
        comparacoes++; // Uma comparação para acessar o slot principal

        // 1. Tenta encontrar na tabela principal
        if (table[index] != null && table[index].getTitle().equals(title)) {
            return index;
        }

        // 2. Se não encontrado na posição principal ou se a posição estava vazia, procura na área de reserva
        // Itera apenas sobre os slots que foram efetivamente preenchidos na reserva
        for (int i = 0; i < nextReserveIndex; i++) {
            comparacoes++; // Uma comparação para cada elemento na reserva
            if (reserveArea[i] != null && reserveArea[i].getTitle().equals(title)) {
                return tamTab + i; // Retorna o índice total (tamanho da tabela principal + índice na reserva)
            }
        }

        return -1; // Não encontrado
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

public class Hash {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // Tamanho da tabela principal (tamTab) é 21. A área de reserva tem tamanho 9.
        // O tamanho total da tabela é 21 + 9 = 30.
        HashTable hashTable = new HashTable(21, 9);
        String pathCSV = "/tmp/disneyplus.csv"; // Caminho do arquivo CSV
        String linha;
        long startTime = System.nanoTime(); // Início da contagem de tempo

        // Parte 1: Leitura dos IDs e inserção na tabela hash
        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show s = new Show();
            s.ler(linha, pathCSV);
            // Verifica o retorno de inserir para saber se foi bem-sucedido
            hashTable.inserir(s);
        }

        // Parte 2: Pesquisa dos títulos na tabela hash
        while (!(linha = sc.nextLine()).equals("FIM")) {
            // Cria um Show temporário apenas para obter o título para pesquisa
            Show sPesquisa = new Show();
            sPesquisa.setTitle(linha);

            int posicao = hashTable.pesquisar(sPesquisa.getTitle());

            // Imprime a saída no formato especificado
            if (posicao != -1) {
                posicao = posicao%20;
                System.out.println(" (Posicao: " + posicao + ") SIM");
            } else {
                // A posição para "NÃO" é arbitrária, mas o exemplo mostra um número.
                // Usaremos o hash inicial como o "local" onde seria procurado, já que foi isso que gerou a dúvida.
                int hashInicial = hashTable.hash(sPesquisa.getTitle());
                System.out.println(" (Posicao: " + hashInicial + ") NAO");
            }
        }

        long endTime = System.nanoTime(); // Fim da contagem de tempo
        long duration = (endTime - startTime) / 1_000_000; // Duração em milissegundos

        // Geração do arquivo de log
        String matricula = "850602"; // Substitua pela sua matrícula
        try (FileWriter fw = new FileWriter(matricula + "_hashReserva.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(matricula + "\t" + duration + "\t" + hashTable.getComparacoes());
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }

        sc.close(); // Fecha o scanner
    }
}