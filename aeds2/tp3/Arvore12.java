import java.util.*;
import java.io.*;

class No {
    public Restaurante elemento;
    public No esq, dir;

    public No(Restaurante elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }
}

class ArvoreBinaria {
    private No raiz;
    public int comparacoes;

    public ArvoreBinaria() {
        raiz = null;
        comparacoes = 0;
    }

    public void inserir(Restaurante r) {
        raiz = inserir(r, raiz);
    }

    private No inserir(Restaurante r, No i) {
        if (i == null) {
            return new No(r);
        }

        int comp = r.getNome().compareTo(i.elemento.getNome());
        if (comp < 0) {
            i.esq = inserir(r, i.esq);
        } else if (comp > 0) {
            i.dir = inserir(r, i.dir);
        }
        return i;
    }

    public void pesquisar(String nome) {
        System.out.print("raiz ");
        pesquisar(nome, raiz);
    }

    private void pesquisar(String nome, No i) {
        if (i == null) {
            System.out.println("NAO");
            return;
        }

        comparacoes++;
        int comp = nome.compareTo(i.elemento.getNome());
        if (comp < 0) {
            System.out.print("esq ");
            pesquisar(nome, i.esq);
        } else if (comp > 0) {
            System.out.print("dir ");
            pesquisar(nome, i.dir);
        } else {
            System.out.println("SIM");
        }
    }

    public void caminharEmOrdem() {
        caminharEmOrdem(raiz);
    }

    private void caminharEmOrdem(No i) {
        if (i != null) {
            caminharEmOrdem(i.esq);
            System.out.println(i.elemento.formatar());
            caminharEmOrdem(i.dir);
        }
    }
}

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
    public String getNome() { return this.nome; }

    public static boolean parseBool(String s) { return s.equals("true"); }

    public static String[] parseCozinha(String s) {
        int i = 0;
        String[] array = new String[200];
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter(";");
        while (scanner.hasNext()) {
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
            this.id, this.nome, this.cidade, this.capacidade, this.avaliacao, 
            this.formatarCozinhas(), "$".repeat(faixaPreco), this.horario, this.dataAbertura.formatar(), this.aberto);
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
        int tam = 0;
        Restaurante[] rests = new Restaurante[5000];
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
        for (int i = 0; i < tamanho; i++) {
            if (restaurantes[i].getId() == id) return restaurantes[i];
        }
        return null;
    }
}

public class Arvore12 {
    public static void main(String[] args) {
        ColecaoRestaurantes cr = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);
        ArvoreBinaria arvore = new ArvoreBinaria();

        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1) break;
            Restaurante r = cr.getRestauranteById(id);
            if (r != null) {
                arvore.inserir(r);
            }
        }

        if (sc.hasNextLine()) sc.nextLine();

        long inicio = System.nanoTime();

        while (sc.hasNextLine()) {
            String nome = sc.nextLine().trim();
            if (nome.equals("FIM") || nome.isEmpty()) break;

            arvore.pesquisar(nome);
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        arvore.caminharEmOrdem();

        try (PrintWriter writer = new PrintWriter(new FileWriter("matrícula_arvore_binaria.txt"))) {
            writer.printf("850602\t%d\t%.6f\n", arvore.comparacoes, tempo);
        } catch (Exception error) {}

        sc.close();
    }
}