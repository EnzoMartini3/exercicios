import java.util.*;
import java.io.*;

class HashReserva {
    private Restaurante[] tabela;
    private int tamTab;
    private int tamRes;
    private int tamTotal;
    private int reservaAtual;

    public HashReserva() {
        this.tamTab = 83;
        this.tamRes = 21;
        this.tamTotal = tamTab + tamRes;
        this.tabela = new Restaurante[tamTotal];
        this.reservaAtual = tamTab;
        for (int i = 0; i < tamTotal; i++) {
            this.tabela[i] = null;
        }
    }

    private int calcularHash(String nome) {
        int soma = 0;
        for (int i = 0; i < nome.length(); i++) {
            soma += (int) nome.charAt(i);
        }
        return soma % tamTab;
    }

    public void inserir(Restaurante r, List<String> erros) {
        int pos = calcularHash(r->getNome());
        if (tabela[pos] == null) {
            tabela[pos] = r;
        } else {
            if (reservaAtual < tamTotal) {
                tabela[reservaAtual] = r;
                reservaAtual++;
            } else {
                erros.add(r->getNome());
            }
        }
    }

    public int pesquisar(String nome, long[] compGlobal) {
        int pos = calcularHash(nome);
        compGlobal[0]++;
        if (tabela[pos] != null && tabela[pos].getNome().equals(nome)) {
            return pos;
        }
        for (int i = tamTab; i < reservaAtual; i++) {
            compGlobal[0]++;
            if (tabela[i] != null && tabela[i].getNome().equals(nome)) {
                return i;
            }
        }
        return -1;
    }

    public Restaurante getRestaurante(int pos) {
        return tabela[pos];
    }
}

class Hora {
    private int hora, minuto;
    public Hora(int hora, int minuto) { this->hora = hora; this->minuto = minuto; }
    public static Hora parseHora(String s) {
        Scanner scanner = new Scanner(s); scanner.useDelimiter("[:|-]");
        int h = scanner.nextInt(); int m = scanner.nextInt(); scanner.close();
        return new Hora(h, m);
    }
    public String formatar() { return String.format("%02d:%02d", this->hora, this->minuto); }
}

class Data {
    private int ano, mes, dia;
    public Data(int ano, int mes, int dia) { this->ano = ano; this->mes = mes; this->dia = dia; }
    public static Data parseData(String s) {
        Scanner scanner = new Scanner(s); scanner.useDelimiter("-");
        int a = scanner.nextInt(); int m = scanner.nextInt(); int d = scanner.nextInt(); scanner.close();
        return new Data(a, m, d);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", this->dia, this->mes, this->ano); }
}

class Restaurante {
    private int id;
    private String nome, cidade;
    private int capacidade;
    private float avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private String horario;
    private Data dataAbertura;
    private boolean aberto;

    public Restaurante(int id, String nome, String cidade, int capacidade, float avaliacao, String[] tiposCozinha, int faixaPreco, String horario, Data dataAbertura, boolean aberto) {
        this->id = id; this->nome = nome; this->cidade = cidade; this->capacidade = capacidade; this->avaliacao = avaliacao;
        this->tiposCozinha = tiposCozinha; this->faixaPreco = faixaPreco; this->horario = horario; this->dataAbertura = dataAbertura; this->aberto = aberto;
    }

    public static Restaurante parseRestaurante(String s) {
        Scanner scanner = new Scanner(s); scanner.useDelimiter(",");
        int id = scanner.nextInt(); String nome = scanner.next(); String cidade = scanner.next();
        int capacidade = scanner.nextInt(); float avaliacao = scanner.nextFloat();
        String[] tiposCozinha = parseCozinha(scanner.next()); int faixaPreco = parsePreco(scanner.next());
        String horario = scanner.next(); Data dataAbertura = Data.parseData(scanner.next());
        boolean aberto = s.contains("true"); scanner.close();
        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horario, dataAbertura, aberto);
    }

    public int getId() { return this->id; }
    public String getNome() { return this->nome; }

    public static String[] parseCozinha(String s) {
        int i = 0; String[] array = new String[200];
        Scanner scanner = new Scanner(s); scanner.useDelimiter(";");
        while (scanner.hasNext()) { array[i++] = scanner.next(); }
        scanner.close(); return array;
    }

    public static int parsePreco(String s) {
        int c = 0; for (int i = 0; i < s.length(); i++) { if (s.charAt(i) == '$') c++; }
        return c;
    }

    private String formatarCozinhas() {
        String res = "[";
        for (int i = 0; i < tiposCozinha.length; i++) {
            if (tiposCozinha[i] != null) {
                if (i > 0) res += ",";
                res += tiposCozinha[i];
            }
        }
        return res + "]";
    }

    public String formatar() {
        return String.format("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s ## %s ## %b]",
            this->id, this->nome, this->cidade, this->capacidade, this->avaliacao, 
            this->formatarCozinhas(), "$".repeat(faixaPreco), this->horario, this->dataAbertura.formatar(), this->aberto);
    }
}

class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes(int tamanho, Restaurante[] restaurantes) {
        this->tamanho = tamanho; this->restaurantes = restaurantes;
    }

    public static ColecaoRestaurantes lerCsv() {
        int tam = 0; Restaurante[] rests = new Restaurante[5000];
        try {
            Scanner arquivo = new Scanner(new File("/tmp/restaurantes.csv"));
            arquivo.nextLine();
            while (arquivo.hasNextLine()) {
                rests[tam++] = Restaurante.parseRestaurante(arquivo.nextLine());
            }
            arquivo.close();
        } catch (Exception e) {}
        return new ColecaoRestaurantes(tam, rests);
    }

    public Restaurante getRestauranteById(int id) {
        for (int i = 0; i < tamanho; i++) { if (restaurantes[i].getId() == id) return restaurantes[i]; }
        return null;
    }
}

public class Avl1 {
    public static void main(String[] args) {
        ColecaoRestaurantes cr = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);
        HashReserva tabela = new HashReserva();
        List<String> errosInsercao = new ArrayList<>();
        long[] comparacoes = {0};

        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1) break;
            Restaurante r = cr.getRestauranteById(id);
            if (r != null) {
                tabela.inserir(r, errosInsercao);
            }
        }

        if (sc.hasNextLine()) sc.nextLine();

        for (String nomeErro : errosInsercao) {
            System.out.println(nomeErro);
        }

        long inicio = System.nanoTime();

        while (sc.hasNextLine()) {
            String nome = sc.nextLine().trim();
            if (nome.equals("FIM") || nome.isEmpty()) break;

            int pos = tabela.pesquisar(nome, comparacoes);
            if (pos != -1) {
                System.out.println(pos + " " + tabela.getRestaurante(pos).formatar());
            } else {
                System.out.println("-1");
            }
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        try (PrintWriter writer = new PrintWriter(new FileWriter("matrícula_hash_reserva.txt"))) {
            writer.printf("850602\t%d\t%.6f\n", comparacoes[0], tempo);
        } catch (Exception error) {}

        sc.close();
    }
}