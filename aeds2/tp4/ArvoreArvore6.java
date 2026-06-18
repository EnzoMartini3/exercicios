import java.util.*;
import java.io.*;

// ==================== SEGUNDO NÍVEL: ÁRVORE AVL ====================
import java.util.*;
import java.io.*;

class NoSub {
    public Restaurante elemento;
    public NoSub esq, dir;
    public int nivel;

    public NoSub(Restaurante elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
        this.nivel = 1;
    }

    public int pegarMaior(int no1, int no2) {
        return (no1 > no2) ? no1 : no2;
    }

    public void setNivel() {
        this.nivel = 1 + pegarMaior(getNivel(this.dir), getNivel(this.esq));
    }

    public int getNivel(NoSub i) {
        return (i == null) ? 0 : i.nivel;
    }

    public int getFator() {
        return getNivel(this.esq) - getNivel(this.dir);
    }
}

class ArvoreAVL {
    private NoSub raiz;

    public ArvoreAVL() {
        raiz = null;
    }

    public void inserir(Restaurante r) {
        raiz = inserir(r, raiz);
    }

    private NoSub inserir(Restaurante r, NoSub i) {
        if (i == null) {
            return new NoSub(r);
        }

        int comp = r.getNome().compareTo(i.elemento.getNome());
        if (comp < 0) {
            i.esq = inserir(r, i.esq);
        } else if (comp > 0) {
            i.dir = inserir(r, i.dir);
        } else {
            return i;
        }

        i.setNivel();
        return balancear(i);
    }

    private NoSub balancear(NoSub i) {
        int factor = i.getFator();

        if (factor > 1) {
            if (i.esq.getFator() < 0) {
                i.esq = rotacionarEsquerda(i.esq);
            }
            return rotacionarDireita(i);
        } else if (factor < -1) {
            if (i.dir.getFator() > 0) {
                i.dir = rotacionarDireita(i.dir);
            }
            return rotacionarEsquerda(i);
        }
        return i;
    }

    private NoSub rotacionarDireita(NoSub y) {
        NoSub x = y.esq;
        NoSub T2 = x.dir;
        x.dir = y;
        y.esq = T2;
        y.setNivel();
        x.setNivel();
        return x;
    }

    private NoSub rotacionarEsquerda(NoSub x) {
        NoSub y = x.dir;
        NoSub T2 = y.esq;
        y.esq = x;
        x.dir = T2;
        x.setNivel();
        y.setNivel();
        return y;
    }

    public boolean pesquisar(String nome, long[] compGlobal) {
        System.out.print("raiz ");
        return pesquisar(nome, raiz, compGlobal);
    }

    private boolean pesquisar(String nome, NoSub i, long[] compGlobal) {
        if (i == null) {
            compGlobal[0]++;
            return false;
        }

        compGlobal[0]++;
        int comp = nome.compareTo(i.elemento.getNome());
        if (comp < 0) {
            System.out.print("esq ");
            return pesquisar(nome, i.esq, compGlobal);
        } else if (comp > 0) {
            System.out.print("dir ");
            return pesquisar(nome, i.dir, compGlobal);
        } else {
            System.out.print("SIM " + i.elemento.formatar() + "\n");
            return true;
        }
    }

    public void caminharEmOrdem() {
        caminharEmOrdem(raiz);
    }

    private void caminharEmOrdem(NoSub i) {
        if (i != null) {
            caminharEmOrdem(i.esq);
            System.out.println(i.elemento.formatar());
            caminharEmOrdem(i.dir);
        }
    }
}

class NoPrincipal {
    public int chave;
    public ArvoreAVL subArvore;
    public NoPrincipal esq, dir;

    public NoPrincipal(int chave) {
        this.chave = chave;
        this.subArvore = new ArvoreAVL();
        this.esq = this.dir = null;
    }
}

class ArvoreDeArvore {
    private NoPrincipal raiz;
    public int comparacoes;

    public ArvoreDeArvore() {
        raiz = null;
        comparacoes = 0;
    }

    public void inserirRestaurante(Restaurante r) {
        int chave = r.getCapacidade() % 15;
        raiz = inserir(chave, r, raiz);
    }

    private NoPrincipal inserir(int chave, Restaurante r, NoPrincipal i) {
        if (i == null) {
            NoPrincipal novo = new NoPrincipal(chave);
            novo.subArvore.inserir(r);
            return novo;
        }

        if (chave < i.chave) {
            i.esq = inserir(chave, r, i.esq);
        } else if (chave > i.chave) {
            i.dir = inserir(chave, r, i.dir);
        } else {
            i.subArvore.inserir(r);
        }
        return i;
    }

    public void pesquisar(String nome) {
        System.out.print("RAIZ ");
        long[] compGlobal = {0};
        if (!pesquisar(nome, raiz, compGlobal)) {
            System.out.println("NAO");
        }
        this.comparacoes += compGlobal[0];
    }

    private boolean pesquisar(String nome, NoPrincipal i, long[] compGlobal) {
        if (i == null) {
            return false;
        }

        boolean achou = i.subArvore.pesquisar(nome, compGlobal);
        if (achou) {
            return true;
        }

        System.out.print("ESQ ");
        if (pesquisar(nome, i.esq, compGlobal)) {
            return true;
        }

        System.out.print("DIR ");
        return pesquisar(nome, i.dir, compGlobal);
    }

    public void caminharEmOrdem() {
        caminharEmOrdem(raiz);
    }

    private void caminharEmOrdem(NoPrincipal i) {
        if (i != null) {
            caminharEmOrdem(i.esq);
            i.subArvore.caminharEmOrdem();
            caminharEmOrdem(i.dir);
        }
    }
}

// ==================== CLASSES DE SUPORTE (IGUAIS AO SEU CÓDIGO) ====================
class Hora {
    private int hora, minuto;
    public Hora(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
    public static Hora parseHora(String s) {
        Scanner scanner = new Scanner(s); scanner.useDelimiter("[:|-]");
        int h = scanner.nextInt(); int m = scanner.nextInt(); scanner.close();
        return new Hora(h, m);
    }
    public String formatar() { return String.format("%02d:%02d", this.hora, this.minuto); }
}

class Data {
    private int ano, mes, dia;
    public Data(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public static Data parseData(String s) {
        Scanner scanner = new Scanner(s); scanner.useDelimiter("-");
        int a = scanner.nextInt(); int m = scanner.nextInt(); int d = scanner.nextInt(); scanner.close();
        return new Data(a, m, d);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano); }
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
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha; this.faixaPreco = faixaPreco; this.horario = horario; this.dataAbertura = dataAbertura; this.aberto = aberto;
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

    public int getId() { return this.id; }
    public String getNome() { return this.nome; }
    public int getCapacidade() { return this.capacidade; }

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
            this.id, this.nome, this.cidade, this.capacidade, this.avaliacao, 
            this.formatarCozinhas(), "$".repeat(faixaPreco), this.horario, this.dataAbertura.formatar(), this.aberto);
    }
}

class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes(int tamanho, Restaurante[] restaurantes) {
        this.tamanho = tamanho; this.restaurantes = restaurantes;
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
public class ArvoreArvore6 {
    public static void main(String[] args) {
        ColecaoRestaurantes cr = ColecaoRestaurantes.lerCsv();
        Scanner sc = new Scanner(System.in);
        ArvoreDeArvore arvorePrincipal = new ArvoreDeArvore();

        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1) break;
            Restaurante r = cr.getRestauranteById(id);
            if (r != null) {
                arvorePrincipal.inserirRestaurante(r);
            }
        }

        if (sc.hasNextLine()) sc.nextLine();

        long inicio = System.nanoTime();

        while (sc.hasNextLine()) {
            String nome = sc.nextLine().trim();
            if (nome.equals("FIM") || nome.isEmpty()) break;

            arvorePrincipal.pesquisar(nome);
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000_000.0;

        try (PrintWriter writer = new PrintWriter(new FileWriter("matrícula_hibrida_arvore_arvore.txt"))) {
            writer.printf("850602\t%d\t%.6f\n", arvorePrincipal.comparacoes, tempo);
        } catch (Exception error) {}

        sc.close();
    }
}