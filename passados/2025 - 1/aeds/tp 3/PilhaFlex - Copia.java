import java.io.*;
import java.util.*;

class Show {
    public String show_id;
    public String type;
    public String title;
    public String director;
    public String[] cast;
    public int cast_count;
    public String country;
    public String date_added;
    public int release_year;
    public String rating;
    public String duration;
    public String[] listed_in;
    public int listed_count;

    public Show() {
        this.show_id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[20];
        this.cast_count = 0;
        this.country = "NaN";
        this.date_added = "NaN";
        this.release_year = 0;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listed_in = new String[20];
        this.listed_count = 0;
    }
}

public class PilhaFlex {
    // Utilitários
    private static String removeQuotesAndTrim(String str) {
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        if (str.endsWith("\"")) {
            str = str.substring(0, str.length() - 1);
        }
        str = str.trim();
        if (str.isEmpty()) {
            return "NaN";
        }
        return str;
    }

    private static void splitAndSort(String src, String[] dest, int[] count) {
        String temp = removeQuotesAndTrim(src);
        if (temp.equals("NaN")) {
            dest[0] = "NaN";
            count[0] = 1;
            return;
        }

        String[] tokens = temp.split(",");
        for (int i = 0; i < tokens.length && count[0] < 20; i++) {
            dest[count[0]++] = removeQuotesAndTrim(tokens[i]);
        }

        // Ordenação
        for (int i = 0; i < count[0] - 1; i++) {
            for (int j = i + 1; j < count[0]; j++) {
                if (dest[i].compareTo(dest[j]) > 0) {
                    String tmp = dest[i];
                    dest[i] = dest[j];
                    dest[j] = tmp;
                }
            }
        }
    }

    private static Show newShow() {
        return new Show();
    }

    private static String[] parseCsvLine(String linha) {
        List<String> tokens = new ArrayList<>();
        boolean dentroAspas = false;
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                tokens.add(token.toString());
                token.setLength(0);
            } else {
                token.append(c);
            }
        }
        tokens.add(token.toString());
        return tokens.toArray(new String[0]);
    }

    private static Show lerShow(String linha) {
        String[] tokens = parseCsvLine(linha);
        Show s = newShow();

        // Preencher todos os campos
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = removeQuotesAndTrim(tokens[i]);
            if (tokens[i].isEmpty()) tokens[i] = "NaN";
        }

        s.show_id = tokens[0];
        s.type = tokens[1];
        s.title = tokens[2];
        s.director = tokens[3];

        // Cast (lista)
        if (!tokens[4].equals("NaN")) {
            int[] castCount = {0};
            splitAndSort(tokens[4], s.cast, castCount);
            s.cast_count = castCount[0];
        }

        s.country = tokens[5];
        s.date_added = tokens[6];

        // release_year (int)
        if (!tokens[7].equals("NaN")) {
            s.release_year = Integer.parseInt(tokens[7]);
        }

        s.rating = tokens[8];
        s.duration = tokens[9];

        // Listed_in (lista)
        if (!tokens[10].equals("NaN")) {
            int[] listedCount = {0};
            splitAndSort(tokens[10], s.listed_in, listedCount);
            s.listed_count = listedCount[0];
        }

        return s;
    }

    private static void imprimirShow(Show s, int ni) {
        System.out.printf("[%d] => %s ## %s ## %s ## %s ## [", ni, s.show_id, s.title, s.type, s.director);
        
        if (s.cast_count == 0) {
            System.out.print("NaN");
        } else {
            for (int i = 0; i < s.cast_count; i++) {
                System.out.print(s.cast[i]);
                if (i < s.cast_count - 1) System.out.print(", ");
            }
        }
        
        System.out.printf("] ## %s ## %s ## %d ## %s ## %s ## [", s.country, s.date_added, s.release_year, s.rating, s.duration);
        
        if (s.listed_count == 0) {
            System.out.print("NaN");
        } else {
            for (int i = 0; i < s.listed_count; i++) {
                System.out.print(s.listed_in[i]);
                if (i < s.listed_count - 1) System.out.print(", ");
            }
        }
        System.out.println("] ##");
    }

    private static Show buscarPorShowId(String filename, String idProcurado) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linha;
        br.readLine(); // cabeçalho
        while ((linha = br.readLine()) != null) {
            String[] tokens = parseCsvLine(linha);
            String id = removeQuotesAndTrim(tokens[0]);
            if (id.equals(idProcurado)) {
                br.close();
                return lerShow(linha);
            }
        }
        br.close();
        return null;
    }

    public static void main(String[] args) throws IOException {
        LinkedList<Show> lista = new LinkedList<>();
        String path = "/tmp/disneyplus.csv";
        Scanner scanner = new Scanner(System.in);
        int ni = 0;

        // Leitura inicial
        while (true) {
            String linha = scanner.nextLine();
            if (linha.equals("FIM")) break;
            Show temp = buscarPorShowId(path, linha);
            if (temp != null) {
                lista.addFirst(temp); // Insere no início
                ni++;
            }
        }

        // Processamento dos comandos
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            String linha = scanner.nextLine();
            String[] partes = linha.split(" ");
            String comando = partes[0];

            if (comando.equals("I")) {
                String id = partes[1];
                Show temp = buscarPorShowId(path, id);
                if (temp != null) {
                    lista.addFirst(temp);
                    ni++;
                }
            } else if (comando.equals("R")) {
                Show removido = lista.removeFirst();
                System.out.printf("(R) %s\n", removido.title);
                ni--;
            }
        }

        // Impressão na ordem correta
        int contador = ni - 1;
        for (Show s : lista) {
            imprimirShow(s, contador--);
        }

        scanner.close();
    }
}