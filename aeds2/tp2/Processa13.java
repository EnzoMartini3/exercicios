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

    public int getAno() { return this.ano; } // Necessário para a média

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

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getDataAberturaAno() { return dataAbertura.getAno(); }

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
        boolean aberto = s.contains("true");
        scanner.close();
        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horario, dataAbertura, aberto);
    }

    private static String[] parseCozinha(String s) {
        return s.split(";");
    }

    private static int parsePreco(String s) {
        return s.length();
    }

    public String formatar() {
        StringBuilder cozinhas = new StringBuilder("[");
        for(int i=0; i<tiposCozinha.length; i++) {
            cozinhas.append(tiposCozinha[i]).append(i == tiposCozinha.length - 1 ? "" : ",");
        }
        cozinhas.append("]");
        return String.format("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s ## %s ## %b]",
            id, nome, cidade, capacidade, avaliacao, cozinhas, "$".repeat(faixaPreco), horario, dataAbertura.formatar(), aberto);
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

class Fila {
    private Restaurante[] array;
    private int primeiro, ultimo;
    public long movimentacoes = 0;

    public Fila(int tamanho) {
        array = new Restaurante[tamanho + 1];
        primeiro = ultimo = 0;
    }

    public void inserir(Restaurante r) throws Exception {
        if (((ultimo + 1) % array.length) == primeiro) {
            Restaurante removido = remover();
            System.out.println("(R)" + removido.getNome());
        }
        array[ultimo] = r;
        ultimo = (ultimo + 1) % array.length;
        movimentacoes++; 
        System.out.println("(I)" + calcularMediaAnos());
    }

    public Restaurante remover() throws Exception {
        if (primeiro == ultimo) throw new Exception("Vazia");
        Restaurante resp = array[primeiro];
        primeiro = (primeiro + 1) % array.length;
        movimentacoes++;
        return resp;
    }

    private int calcularMediaAnos() {
        double soma = 0;
        int cont = 0;
        for (int i = primeiro; i != ultimo; i = (i + 1) % array.length) {
            soma += array[i].getDataAberturaAno();
            cont++;
        }
        return (cont == 0) ? 0 : (int) Math.round(soma / cont);
    }

    public void mostrar() {
        for (int i = primeiro; i != ultimo; i = (i + 1) % array.length) {
            System.out.println(array[i].formatar());
        }
    }
}




public class Processa13 {public static void main(String[] args) {
        ColecaoRestaurantes colecao_completa = ColecaoRestaurantes.lerCsv();
        Scanner scanner = new Scanner(System.in);
        Fila fila = new Fila(5);

        // IDs Iniciais
        while (scanner.hasNext()) {
            String entrada = scanner.next();
            if (entrada.equals("-1")) break;
            Restaurante r = colecao_completa.getRestauranteById(Integer.parseInt(entrada));
            if (r != null) {
                try { fila.inserir(r); } catch (Exception e) {}
            }
        }

        long inicio = System.nanoTime();

        // Comandos I/R
        if (scanner.hasNextInt()) {
            int numComandos = scanner.nextInt();
            for (int i = 0; i < numComandos; i++) {
                try {
                    String cmd = scanner.next();
                    if (cmd.equals("I")) {
                        fila.inserir(colecao_completa.getRestauranteById(scanner.nextInt()));
                    } else if (cmd.equals("R")) {
                        Restaurante rem = fila.remover();
                        System.out.println("(R) " + rem.getNome());
                    }
                } catch (Exception e) {}
            }
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        fila.mostrar();

        // LOG
        try (PrintWriter writer = new PrintWriter(new FileWriter("850602_filaCircular.txt"))) {
            writer.printf("850602\t%d\t%.6f\n", fila.movimentacoes, tempo);
        } catch (Exception error) {}

        scanner.close();
    }
}