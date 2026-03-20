import java.io.*;
import java.util.*;

// Classe Show existente, com uma pequena adição para a chave da primeira árvore
class Show implements Comparable<Show> {
    String showId;
    String type;
    String title;
    String director;
    String[] cast;
    String country;
    String dateAdded;
    int releaseYear;
    String rating;
    String duration;
    String[] listedIn;

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

    // Adiciona um getter para a chave da primeira árvore
    public int getReleaseYearMod15() {
        return this.releaseYear % 15;
    }

    // Método clone
    public Show clone() {
        return new Show(showId, type, title, director, cast.clone(), country, dateAdded, releaseYear, rating, duration, listedIn.clone());
    }

    // Método de impressão
    public void imprimir() {
        System.out.print(showId + " ## " + title + " ## " + type + " ## " + director + " ## ");
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

    String[] sortArray(String[] array) {
        if (array != null && array.length > 0) {
            Arrays.sort(array);
        }
        return array;
    }

    String[] ordenarCampoArray(String campo) {
        if (campo == null || campo.trim().isEmpty()) return new String[0];
        String[] partes = campo.split(",\\s*");
        Arrays.sort(partes);
        return partes;
    }

    String[] dividirCSV(String linha) {
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

    // A comparação padrão de Show ainda é pelo título, mas não será usada diretamente
    // na inserção/pesquisa principal da Arvore alternada.
    @Override
    public int compareTo(Show outroShow) {
        return this.title.compareTo(outroShow.title);
    }
}

class NoPrimario {
    int chave; // releaseYear mod 15
    NoPrimario esq, dir;
    NoSecundario arvoreSecundaria;

    NoPrimario(int chave) {
        this.chave = chave;
        this.esq = null;
        this.dir = null;
        this.arvoreSecundaria = null;
    }
}

class NoSecundario {
    String chave; // title
    Show show;
    NoSecundario esq, dir;

    NoSecundario(Show show) {
        this.chave = show.getTitle();
        this.show = show;
        this.esq = null;
        this.dir = null;
    }
}

public class ArvoreArvores {
    private NoPrimario raiz;
    private long comparacoes;

    public ArvoreArvores() {
        this.raiz = null;
        this.comparacoes = 0;
    }

    // Insere um show na estrutura
    public void inserir(Show s) {
        int chavePrimaria = s.getReleaseYear() % 15;
        raiz = inserirPrimario(s, chavePrimaria, raiz);
    }

    // Insere no nó primário ou cria um novo se necessário
    private NoPrimario inserirPrimario(Show s, int chave, NoPrimario no) {
        if (no == null) {
            NoPrimario novoNo = new NoPrimario(chave);
            novoNo.arvoreSecundaria = inserirSecundario(s, novoNo.arvoreSecundaria);
            return novoNo;
        }

        if (chave < no.chave) {
            no.esq = inserirPrimario(s, chave, no.esq);
        } else if (chave > no.chave) {
            no.dir = inserirPrimario(s, chave, no.dir);
        } else {
            // Chave primária já existe, insere na árvore secundária
            no.arvoreSecundaria = inserirSecundario(s, no.arvoreSecundaria);
        }
        return no;
    }

    // Insere na árvore secundária
    private NoSecundario inserirSecundario(Show s, NoSecundario no) {
        if (no == null) {
            return new NoSecundario(s);
        }

        int cmp = s.getTitle().compareTo(no.chave);
        if (cmp < 0) {
            no.esq = inserirSecundario(s, no.esq);
        } else if (cmp > 0) {
            no.dir = inserirSecundario(s, no.dir);
        }
        // Se cmp == 0, título já existe (não faz nada ou trata duplicatas conforme necessário)
        return no;
    }

    // Pesquisa um show pelo título
    public boolean pesquisar(String titulo) {
        StringBuilder path = new StringBuilder("raiz");
        boolean encontrado = pesquisarPrimario(titulo, raiz, path); // Inicia a pesquisa na árvore primária
        System.out.println(path.toString() + (encontrado ? " SIM" : " NAO"));
        return encontrado;
    }

    // Pesquisa na árvore primária, controlando a capitalização
    private boolean pesquisarPrimario(String titulo, NoPrimario no, StringBuilder path) {
        if (no == null) {
            return false;
        }

        // 1. Tenta pesquisar na árvore secundária associada a este nó primário
        boolean encontradoSecundario = pesquisarSecundario(titulo, no.arvoreSecundaria, path);
        if (encontradoSecundario) {
            return true; // Encontrou na árvore secundária deste nó primário
        }

        // 2. Se não encontrou, tenta na sub-árvore esquerda da árvore primária
        // Adiciona " ESQ" MAIÚSCULO antes de descer para a esquerda na árvore primária
        if (no.esq != null) {
            path.append("  ESQ");
            if (pesquisarPrimario(titulo, no.esq, path)) {
                return true;
            }
        }

        // 3. Se não encontrou na esquerda, tenta na sub-árvore direita da árvore primária
        // Adiciona " DIR" MAIÚSCULO antes de descer para a direita na árvore primária
        if (no.dir != null) {
            path.append("  DIR");
            if (pesquisarPrimario(titulo, no.dir, path)) {
                return true;
            }
        }
        
        return false; // Não encontrou em nenhuma sub-árvore primária ou na sua secundária
    }

    // Pesquisa em uma árvore secundária (sempre com minúsculas)
    private boolean pesquisarSecundario(String titulo, NoSecundario no, StringBuilder path) {
        if (no == null) {
            return false;
        }

        comparacoes++;
        int cmp = titulo.compareTo(no.chave);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            path.append(" esq"); // Dentro da árvore secundária, minúsculas
            return pesquisarSecundario(titulo, no.esq, path);
        } else {
            path.append(" dir"); // Dentro da árvore secundária, minúsculas
            return pesquisarSecundario(titulo, no.dir, path);
        }
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public void resetComparacoes() {
        this.comparacoes = 0;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ArvoreArvores arv = new ArvoreArvores();
        String pathCSV = "/tmp/disneyplus.csv";
        String linha;
        long startTime = System.nanoTime();

        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show s = new Show();
            s.ler(linha, pathCSV); 
            arv.inserir(s);
        }

        while (!(linha = sc.nextLine()).equals("FIM")) {
            arv.pesquisar(linha);
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        String matricula = "850602";
        try (FileWriter fw = new FileWriter(matricula + "_ArvoreArvores.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(matricula + "\t" + duration + "\t" + arv.getComparacoes());
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }

        sc.close();
    }
}