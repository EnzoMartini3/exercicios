import java.util.*;
import java.io.*;

class Hora { 
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public static Hora parseHora(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("[:|-]");
        int hora = scanner.nextInt();
        int minuto = scanner.nextInt();
        scanner.close();
        return new Hora(hora, minuto);
    }

    public String formatar() {
        return String.format("%02d:%02d", this.hora, this.minuto);
    }
}

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public int getAno() { return this.ano; }
    public int getMes() { return this.mes; }
    public int getDia() { return this.dia; }

    public static Data parseData(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("-");
        int ano = scanner.nextInt();
        int mes = scanner.nextInt();
        int dia = scanner.nextInt();
        scanner.close();
        return new Data(ano, mes, dia);
    }

    public String formatar() {
        return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano);
    }
}

class Restaurante {
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private float avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private String horario;
    private Data dataAbertura;
    private boolean aberto;

    public Restaurante(int id, String nome, String cidade, int capacidade, float avaliacao, String[] tiposCozinha, int faixaPreco, String horario, Data dataAbertura, boolean aberto) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha;
        this.faixaPreco = faixaPreco;
        this.horario = horario;
        this.dataAbertura = dataAbertura;
        this.aberto = aberto;
    }

    public static Restaurante parseRestaurante(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useLocale(Locale.US);
        scanner.useDelimiter(",");
        int id = scanner.nextInt();
        String nome = scanner.next(); 
        String cidade = scanner.next();
        int capacidade = scanner.nextInt();
        float avaliacao = scanner.nextFloat();
        String[] tiposCozinha = parseCozinha(scanner.next());
        int faixaPreco = parsePreco(scanner.next());
        String horario = scanner.next();
        Data dataAbertura = Data.parseData(scanner.next());
        boolean aberto = parseBool(scanner.next());
        scanner.close();
        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horario, dataAbertura, aberto);
    }

    public int getId() { return this.id; }
    public String getCidade() { return this.cidade; }
    public String getNome() { return this.nome; }
    public Data getDataAbertura() { return this.dataAbertura; }

    public static boolean parseBool(String s) { return s.equals("true"); }

    public static String[] parseCozinha(String s) {
        int i = 0;
        String[] restaurantes = new String[200];
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter(";");
        while(scanner.hasNext()){
            restaurantes[i] = scanner.next();
            i++;
        }
        scanner.close();
        return restaurantes;
    }

    public static int parsePreco(String s) {
        int contador = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '$') contador++;
        }
        return contador;
    }

    private String formatarCozinhas() {
        StringBuilder res = new StringBuilder("[");
        boolean primeiro = true;
        for (String cozinha : tiposCozinha) {
            if (cozinha != null) {
                if (!primeiro) res.append(",");
                res.append(cozinha);
                primeiro = false;
            }
        }
        return res.append("]").toString();
    }

    private String formatarPreco() {
        return "$".repeat(Math.max(0, faixaPreco));
    }

    public String formatar() {
        return String.format("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s ## %s ## %b]",
            this.id, this.nome, this.cidade, this.capacidade, this.avaliacao, 
            this.formatarCozinhas(), this.formatarPreco(), this.horario,
            this.dataAbertura.formatar(), this.aberto);
    }
}

class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes(int tamanho, Restaurante[] restaurantes) {
        this.tamanho = tamanho;
        this.restaurantes = restaurantes;
    }

    public static ColecaoRestaurantes lerCsv() {
        int contador = 0;
        Restaurante[] colecao = new Restaurante[1000];
        try (Scanner arquivo = new Scanner(new File("/tmp/restaurantes.csv"))) {
            if (arquivo.hasNextLine()) arquivo.nextLine();
            while (arquivo.hasNextLine()) {
                colecao[contador++] = Restaurante.parseRestaurante(arquivo.nextLine());
            }       
        } catch (Exception error) {}
        return new ColecaoRestaurantes(contador, colecao);
    }

    public Restaurante getRestauranteById(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (restaurantes[i].getId() == id) return restaurantes[i];
        }
        return null;
    }
}

public class Processa05 {

    public static boolean pesquisar(Restaurante[] vetor, int n, String alvo, long[] qntd_comparacoes) {
        for (int i = 0; i < n; i++) {
            qntd_comparacoes[0]++;
            if (vetor[i].getNome().equals(alvo)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Scanner scanner = new Scanner(System.in);
        
        Restaurante[] listaBusca = new Restaurante[1000];
        int n = 0;
        long[] qntd_comparacoes = {0};

        boolean continuar = true;
        while (continuar && scanner.hasNext()) {
            String entrada = scanner.next();
            if (entrada.equals("FIM") || entrada.equals("-1")) {
                continuar = false;
            } else {
                try {
                    int id = Integer.parseInt(entrada);
                    Restaurante r = colecao.getRestauranteById(id);
                    if (r != null) listaBusca[n++] = r;
                } catch (Exception erro) {}
            }
        }

        if (scanner.hasNextLine()) scanner.nextLine();

        long inicio = System.nanoTime();
        boolean lendo = true;

        while (lendo && scanner.hasNextLine()) {
            String nomes_Restaurante = scanner.nextLine().trim();
            if (nomes_Restaurante.isEmpty()) continue;
            if (nomes_Restaurante.equals("FIM")) {
                lendo = false;
            } else {
                if (pesquisar(listaBusca, n, nomes_Restaurante, qntd_comparacoes)) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO");
                }
            }
        }
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        try (PrintWriter writer = new PrintWriter(new FileWriter("850602_<sequencial>.txt"))) {

            writer.printf("850602\t%d\t%.6f\n", qntd_comparacoes[0], tempo);

        } catch (Exception erro) {}
        
        scanner.close();
    }
}
