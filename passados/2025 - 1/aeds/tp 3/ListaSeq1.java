/*Crie uma Lista de registros baseada na de inteiros vista na sala de aula. Sua lista deve conter todos os atributos e métodos existentes na lista de inteiros, contudo, adaptados para a classe Show. Lembre-se que, na verdade, temos uma lista de ponteiros (ou referências) e cada um deles aponta para um registo. Neste exercício, faremos inserções, remoções e mostraremos os elementos de nossa lista.

Os métodos de inserir e remover devem operar conforme descrito a seguir, respeitando parâmetros e retornos. Primeiro, o void inserirInicio(Show show) insere um registro na primeira posição da Lista e remaneja os demais. Segundo, o void inserir(Show show, int posição) insere um registro na posição $p$ da Lista, onde p < n e n é o número de registros cadastrados. Em seguida, esse método remaneja os demais registros. O void inserirFim(Show show) insere um registro na última posição da Lista. O Show removerInicio() remove e retorna o primeiro registro cadastrado na Lista e remaneja os demais. O Show remover(int posição) remove e retorna o registro cadastrado na p-ésima posição da Lista e remaneja os demais. O Show removerFim() remove e retorna o último registro cadastrado na lista.

A entrada padrão é composta por duas partes. A primeira é igual a entrada da primeira questão do TP02. As demais linhas correspondem a segunda parte. A primeira linha da segunda parte tem um número inteiro n indicando a quantidade de registros a serem inseridos/removidos. Nas próximas n linhas, tem-se n comandos de inserção/remoção a serem processados neste exercício. Cada uma dessas linhas tem uma palavra de comando: II inserir no início, I* inserir em qualquer posição, IF inserir no fim, RI remover no início, R* remover em qualquer posição e RF remover no fim. No caso dos comandos de inserir, temos também o nome do arquivo que contém o registro a ser inserido. No caso dos comandos de em qualquer posição, temos também esse nome. No Inserir, a posição fica imediatamente após a palavra de comando. A saída padrão tem uma linha para cada registro removido sendo que essa informação será constituída pela palavra ``(R)'' e o atributo title. No final, a saída mostra os atributos relativos a cada registro cadastrado na lista após as operações de inserção e remoção.*/

import java.io.*;
import java.util.*;

class Show implements Cloneable {
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
}

/*Crie uma Lista de registros baseada na de inteiros vista na sala de aula. Sua lista deve conter todos os atributos e métodos existentes na lista de inteiros, contudo, adaptados para a classe Show. Lembre-se que, na verdade, temos uma lista de ponteiros (ou referências) e cada um deles aponta para um registo. Neste exercício, faremos inserções, remoções e mostraremos os elementos de nossa lista.

Os métodos de inserir e remover devem operar conforme descrito a seguir, respeitando parâmetros e retornos. Primeiro, o void inserirInicio(Show show) insere um registro na primeira posição da Lista e remaneja os demais. Segundo, o void inserir(Show show, int posição) insere um registro na posição $p$ da Lista, onde p < n e n é o número de registros cadastrados. Em seguida, esse método remaneja os demais registros. O void inserirFim(Show show) insere um registro na última posição da Lista. O Show removerInicio() remove e retorna o primeiro registro cadastrado na Lista e remaneja os demais. O Show remover(int posição) remove e retorna o registro cadastrado na p-ésima posição da Lista e remaneja os demais. O Show removerFim() remove e retorna o último registro cadastrado na lista.

A entrada padrão é composta por duas partes. A primeira é igual a entrada da primeira questão do TP02. As demais linhas correspondem a segunda parte. A primeira linha da segunda parte tem um número inteiro n indicando a quantidade de registros a serem inseridos/removidos. Nas próximas n linhas, tem-se n comandos de inserção/remoção a serem processados neste exercício. Cada uma dessas linhas tem uma palavra de comando: II inserir no início, I* inserir em qualquer posição, IF inserir no fim, RI remover no início, R* remover em qualquer posição e RF remover no fim. No caso dos comandos de inserir, temos também o nome do arquivo que contém o registro a ser inserido. No caso dos comandos de em qualquer posição, temos também esse nome. No Inserir, a posição fica imediatamente após a palavra de comando. A saída padrão tem uma linha para cada registro removido sendo que essa informação será constituída pela palavra ``(R)'' e o atributo title. No final, a saída mostra os atributos relativos a cada registro cadastrado na lista após as operações de inserção e remoção.*/

class Celula{
    Show show;
    Celula prox;

    Celula(Show show){
        this.show = show;
        this.prox = null;
    }
}

public class ListaSeq1{
    private Celula prim; //primeiro
    private Celula ult; //ultimo


    ListaSeq1(){
        this.prim = null;
        this.ult = null;
    }

    void inserirInicio(Show show){ //insere na primeira pos
        Celula nova = new Celula(show);
        nova.prox = prim;
        prim = nova;
        if(ult == null){
            ult = nova;
        }
    }

        void inserirPos(Show show, int pos) {
        if (pos == 0) {
            inserirInicio(show);
            return;
        }
        Celula ant = prim;
        for (int i = 0; i < pos - 1; i++) {
            ant = ant.prox;
        }
        Celula nova = new Celula(show);
        nova.prox = ant.prox;
        ant.prox = nova;
        if (nova.prox == null) ult = nova;
    }


    void inserirFim(Show show){
        Celula nova = new Celula(show);
        if (prim == null) {
            prim = ult = nova;
        } else {
            ult.prox = nova;
            ult = nova;
        }
    }


    Show removerInicio() {
        if (prim == null) return null;
        Show x = prim.show;
        prim = prim.prox;
        if (prim == null) ult = null;
        return x;
    }


    Show removerPos(int pos) {
        if (pos == 0) return removerInicio();
        Celula ant = prim;
        for (int i = 0; i < pos - 1; i++) {
            ant = ant.prox;
        }
        Celula removida = ant.prox;
        ant.prox = removida.prox;
        if (removida == ult) ult = ant;
        return removida.show;
    }


    Show removerFim() {
        if (prim == null) return null;
        if (prim == ult) {
            Show x = prim.show;
            prim = ult = null;
            return x;
        }
        Celula aux = prim;
        while (aux.prox != ult) aux = aux.prox;
        Show x = ult.show;
        aux.prox = null;
        ult = aux;
        return x;
    }

    void mostrar() {
        for (Celula i = prim; i != null; i = i.prox) {
            i.show.imprimir();
        }
    }


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ListaSeq1 lista = new ListaSeq1();
        String path = "/tmp/disneyplus.csv";
        String linha;

        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show s = new Show();
            s.ler(linha, path);
            lista.inserirFim(s);
        }

        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            linha = sc.nextLine();
            String[] partes = linha.split(" ");
            String comando = partes[0];

            switch (comando) {
                case "II":
                    Show s1 = new Show();
                    s1.ler(partes[1], path);
                    lista.inserirInicio(s1);
                    break;
                case "IF":
                    Show s2 = new Show();
                    s2.ler(partes[1], path);
                    lista.inserirFim(s2);
                    break;
                case "I*":
                    int pos = Integer.parseInt(partes[1]);
                    Show s3 = new Show();
                    s3.ler(partes[2], path);
                    lista.inserirPos(s3, pos);
                    break;
                case "RI":
                    Show rem1 = lista.removerInicio();
                    if (rem1 != null) System.out.println("(R) " + rem1.getTitle());
                    break;
                case "RF":
                    Show rem2 = lista.removerFim();
                    if (rem2 != null) System.out.println("(R) " + rem2.getTitle());
                    break;
                case "R*":
                    int posR = Integer.parseInt(partes[1]);
                    Show rem3 = lista.removerPos(posR);
                    if (rem3 != null) System.out.println("(R) " + rem3.getTitle());
                    break;
            }
        }

        lista.mostrar();
        sc.close();
    }


}


