
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

    //construtor que inicia tudo com nan
    public Show() {
        this("NaN", "NaN", "NaN", "NaN", new String[0], "NaN", "NaN", 0, "NaN", "NaN", new String[0]);
    }
    //construtor rápido
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

    //get/set
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

    //clone
    public Show clone() {
        return new Show(showId, type, title, director, cast.clone(), country, dateAdded, releaseYear, rating, duration, listedIn.clone());
    }

    //impressao
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


    public void ler(String targetId, String pathCSV) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(pathCSV));
        String linha = br.readLine();
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

    public int compareTo(Show outroShow) {
        // A comparação deve ser pelo título (name)
        return this.title.compareTo(outroShow.title);
    }
}

/*Crie uma Lista de registros baseada na de inteiros vista na sala de aula. Sua lista deve conter todos os atributos e métodos existentes na lista de inteiros, contudo, adaptados para a classe Show. Lembre-se que, na verdade, temos uma lista de ponteiros (ou referências) e cada um deles aponta para um registo. Neste exercício, faremos inserções, remoções e mostraremos os elementos de nossa lista.

Os métodos de inserir e remover devem operar conforme descrito a seguir, respeitando parâmetros e retornos. Primeiro, o void inserirInicio(Show show) insere um registro na primeira posição da Lista e remaneja os demais. Segundo, o void inserir(Show show, int posição) insere um registro na posição $p$ da Lista, onde p < n e n é o número de registros cadastrados. Em seguida, esse método remaneja os demais registros. O void inserirFim(Show show) insere um registro na última posição da Lista. O Show removerInicio() remove e retorna o primeiro registro cadastrado na Lista e remaneja os demais. O Show remover(int posição) remove e retorna o registro cadastrado na p-ésima posição da Lista e remaneja os demais. O Show removerFim() remove e retorna o último registro cadastrado na lista.

A entrada padrão é composta por duas partes. A primeira é igual a entrada da primeira questão do TP02. As demais linhas correspondem a segunda parte. A primeira linha da segunda parte tem um número inteiro n indicando a quantidade de registros a serem inseridos/removidos. Nas próximas n linhas, tem-se n comandos de inserção/remoção a serem processados neste exercício. Cada uma dessas linhas tem uma palavra de comando: II inserir no início, I* inserir em qualquer posição, IF inserir no fim, RI remover no início, R* remover em qualquer posição e RF remover no fim. No caso dos comandos de inserir, temos também o nome do arquivo que contém o registro a ser inserido. No caso dos comandos de em qualquer posição, temos também esse nome. No Inserir, a posição fica imediatamente após a palavra de comando. A saída padrão tem uma linha para cada registro removido sendo que essa informação será constituída pela palavra ``(R)'' e o atributo title. No final, a saída mostra os atributos relativos a cada registro cadastrado na lista após as operações de inserção e remoção.*/

class No{
    Show show;
    No esq, dir;

    No(Show show){
        this.show = show;
        this.esq = null;
        this.dir = null;
    }
}

public class Arvore {
    private No raiz;
    private long comparacoes; // Contador de comparações

    public Arvore() {
        this.raiz = null;
        this.comparacoes = 0;
    }

    void inserir(Show s){ //insere na primeira pos
        raiz = inserir(s, raiz);
    }
    No inserir(Show s, No no) {
        if (no == null) {
            no = new No(s);
        } else {
            int cmp = s.compareTo(no.show);
            if (cmp < 0) {
                no.esq = inserir(s, no.esq);
            } else if (cmp > 0) { 
                no.dir = inserir(s, no.dir);
            }
        }
        return no;
    }

    public boolean pesquisar(Show s) {
        StringBuilder path = new StringBuilder("=>raiz");
        boolean encontrado = pesquisar(s, raiz, path);
        System.out.println(path.toString() + (encontrado ? " SIM" : " NAO"));
        return encontrado;
    }

    private boolean pesquisar(Show s, No no, StringBuilder path) {
        if (no == null) {
            return false;
        }
        comparacoes++; // Incrementa o contador de comparações
        int cmp = s.compareTo(no.show);

        if (cmp == 0) { // Encontrou
            return true;
        } else if (cmp < 0) { // s.title é menor, vai para a esquerda
            path.append(" esq");
            return pesquisar(s, no.esq, path);
        } else { // s.title é maior, vai para a direita
            path.append(" dir");
            return pesquisar(s, no.dir, path);
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
        Arvore arv = new Arvore();
        String pathCSV = "/tmp/disneyplus.csv";
        String linha;
        long startTime = System.nanoTime();


        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show s = new Show();
            s.ler(linha, pathCSV); 
            arv.inserir(s);
        }

        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show sPesquisa = new Show();
            sPesquisa.setTitle(linha); 
            arv.pesquisar(sPesquisa);
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        String matricula = "850602";
        try (FileWriter fw = new FileWriter(matricula + "matrícula_arvoreBinaria.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(matricula + "\t" + duration + "\t" + arv.getComparacoes());
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }

        sc.close();
    }
}