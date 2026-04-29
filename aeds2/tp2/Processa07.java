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

public class Processa07 {

    public static void intercalar(Restaurante[] array, int esquerda, int meio, int direita, long[] qntd_Comparacoes) {
        int n1, n2, i, j, k;

        n1 = meio - esquerda + 1;
        n2 = direita - meio;

        Restaurante[] a1 = new Restaurante[n1 + 1];
        Restaurante[] a2 = new Restaurante[n2 + 1];

        for (i = 0; i < n1; i++) {
            a1[i] = array[esquerda + i];
        }

        for (j = 0; j < n2; j++) {
            a2[j] = array[meio + j + 1];
        }

        a1[n1] = null; 
        a2[n2] = null;

        for (i = j = 0, k = esquerda; k <= direita; k++) {
            qntd_Comparacoes[0]++;
            if (comparar(a1[i], a2[j])) {
                array[k] = a1[i++];
            } else {
                array[k] = a2[j++];
            }
        }
    }

    private static boolean comparar(Restaurante r1, Restaurante r2) {
        if (r1 == null) return false;
        if (r2 == null) return true;

        int compCidade = r1.getCidade().compareTo(r2.getCidade());
        if (compCidade < 0) return true;
        if (compCidade > 0) return false;

        return r1.getNome().compareTo(r2.getNome()) <= 0;
    }

    public static void mergesort(Restaurante[] array, int esquerda, int direita, long[] qntd_Comparacoes) {

        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            mergesort(array, esquerda, meio, qntd_Comparacoes);
            mergesort(array, meio + 1, direita, qntd_Comparacoes);
            intercalar(array, esquerda, meio, direita, qntd_Comparacoes);
        }
    }

    public static void main(String[] args) {

        ColecaoRestaurantes colecao_completa = ColecaoRestaurantes.lerCsv();
        Scanner scanner = new Scanner(System.in);
        
        Restaurante[] vetor_ID = new Restaurante[1000];
        int tamanho_Vetor = 0;
        long[] qntd_comparacoes = {0};

        while (scanner.hasNext()) {
            String entrada = scanner.next();
            if (entrada.equals("FIM") || entrada.equals("-1")) break;
            try {
                int valor_ID = Integer.parseInt(entrada);
                Restaurante r = colecao_completa.getRestauranteById(valor_ID);
                if (r != null) {
                    vetor_ID[tamanho_Vetor++] = r;
                }
            } catch (Exception error) {}
        }

        long inicio = System.nanoTime();
        if (tamanho_Vetor > 0) {
            mergesort(vetor_ID, 0, tamanho_Vetor - 1, qntd_comparacoes);
        }
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        for (int i = 0; i < tamanho_Vetor; i++) {
            System.out.println(vetor_ID[i].formatar());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("850602_<mergesort>.txt"))) {
            writer.printf("850602\t%d\t%.6f\n", qntd_comparacoes[0], tempo);
        } catch (Exception error) {}

        scanner.close();
        
    }
}