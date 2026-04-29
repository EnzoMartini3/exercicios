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

    public static boolean parseBool(String s) { return s.equals("true"); }

    public static String[] parseCozinha(String s) {
        int i = 0;
        String[] array = new String[200];
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter(";");
        while(scanner.hasNext()){
            array[i] = scanner.next();
            i++;
        }
        scanner.close();
        return array;
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

class Lista {
    private Restaurante[] array;
    private int n;
    public long movimentacoes; // Contador para o log

    public Lista(int tamanho) {
        array = new Restaurante[tamanho];
        n = 0;
        movimentacoes = 0;
    }

    public void inserirInicio(Restaurante r) throws Exception {
        if (n >= array.length) throw new Exception("Erro!");
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
            movimentacoes++;
        }
        array[0] = r;
        n++;
    }

    public void inserirFim(Restaurante r) throws Exception {
        if (n >= array.length) throw new Exception("Erro!");
        array[n] = r;
        n++;
    }

    public void inserir(Restaurante r, int pos) throws Exception {
        if (n >= array.length || pos < 0 || pos > n) throw new Exception("Erro!");
        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
            movimentacoes++;
        }
        array[pos] = r;
        n++;
    }

    public Restaurante removerInicio() throws Exception {
        if (n == 0) throw new Exception("Erro!");
        Restaurante resp = array[0];
        n--;
        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
            movimentacoes++;
        }
        return resp;
    }

    public Restaurante removerFim() throws Exception {
        if (n == 0) throw new Exception("Erro!");
        return array[--n];
    }

    public Restaurante remover(int pos) throws Exception {
        if (n == 0 || pos < 0 || pos >= n) throw new Exception("Erro!");
        Restaurante resp = array[pos];
        n--;
        for (int i = pos; i < n; i++) {
            array[i] = array[i + 1];
            movimentacoes++;
        }
        return resp;
    }

    public void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println(array[i].formatar());
        }
    }
}

public class Processa11 {
    public static void main(String[] args) {
        ColecaoRestaurantes colecao_completa = ColecaoRestaurantes.lerCsv();
        Scanner scanner = new Scanner(System.in);
        Lista lista = new Lista(1000);

        // 1. Entrada de IDs iniciais
        while (scanner.hasNext()) {
            String entrada = scanner.next();
            if (entrada.equals("-1")) break;
            Restaurante r = colecao_completa.getRestauranteById(Integer.parseInt(entrada));
            if (r != null) {
                try { lista.inserirFim(r); } catch (Exception e) {}
            }
        }

        // 2. Processamento de comandos com medição de tempo
        long inicio = System.nanoTime();
        
        if (scanner.hasNextInt()) {
            int numComandos = scanner.nextInt();
            for (int i = 0; i < numComandos; i++) {
                try {
                    String comando = scanner.next();
                    Restaurante r;
                    switch (comando) {
                        case "II":
                            lista.inserirInicio(colecao_completa.getRestauranteById(scanner.nextInt()));
                            break;
                        case "IF":
                            lista.inserirFim(colecao_completa.getRestauranteById(scanner.nextInt()));
                            break;
                        case "I*":
                            int posI = scanner.nextInt();
                            lista.inserir(colecao_completa.getRestauranteById(scanner.nextInt()), posI);
                            break;
                        case "RI":
                            r = lista.removerInicio();
                            System.out.println("(R)" + r.getNome());
                            break;
                        case "RF":
                            r = lista.removerFim();
                            System.out.println("(R)" + r.getNome());
                            break;
                        case "R*":
                            r = lista.remover(scanner.nextInt());
                            System.out.println("(R)" + r.getNome());
                            break;
                    }
                } catch (Exception e) {}
            }
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        // 3. Saída final e Log
        lista.mostrar();

        try (PrintWriter writer = new PrintWriter(new FileWriter("850602_listaSequencial.txt"))) {
            writer.printf("850602\t%d\t%.6f\n", lista.movimentacoes, tempo);
        } catch (Exception error) {}

        scanner.close();
    }
}