/*Faça a inserção de alguns registros no final de um vetor e, em seguida, faça algumas pesquisas sequenciais. A chave primária de pesquisa será o atributo title. A entrada padrão é composta por duas partes onde a primeira é igual a entrada da primeira questão. As demais linhas correspondem a segunda parte. Cada linha possui um elemento que deve ser pesquisado no vetor até o FIM.

A saída padrão será composta por várias linhas contendo as palavras SIM/NAO para indicar se existe cada um dos elementos pesquisados. Além disso, crie um arquivo de log na pasta corrente com o nome matrícula\_sequencial.txt com uma única linha contendo sua matrícula, tempo de execução do seu algoritmo e número de comparações. Todas as informações do arquivo de log devem ser separadas por uma tabulação '\t'. */

import java.io.*;
import java.util.*;

public class Sequencial implements Cloneable {
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

    //construtor que inicia tudo com not a number para o caso de algum atributo faltar
    public Sequencial() {
        this("NaN", "NaN", "NaN", "NaN", new String[0], "NaN", "NaN", 0, "NaN", "NaN", new String[0]);
    }
    //construtor rápido
    public Sequencial(String showId, String type, String title, String director, String[] cast, String country, String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
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
    public Sequencial clone() {
        return new Sequencial(showId, type, title, director, cast.clone(), country, dateAdded, releaseYear, rating, duration, listedIn.clone());
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


    public void ler(String targetId, String pathCSV)throws Exception{
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

//main
    public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    ArrayList<Sequencial> lista = new ArrayList<>();
    String path = "/tmp/disneyplus.csv";
    String entrada;
    long inicio = System.nanoTime();//timer
    int comparacoes = 0;

    while (!(entrada = sc.nextLine()).equals("FIM")) {
        Sequencial s = new Sequencial();
        s.ler(entrada, path);
        lista.add(s.clone());
    }

    // Parte 2 - Pesquisa sequencial por título
    while (!(entrada = sc.nextLine()).equals("FIM")) {
        boolean encontrado = false;
        for (Sequencial s : lista) {
            comparacoes++;
            if (s.getTitle().equals(entrada)) {
                encontrado = true;
                break;
            }
        }
        System.out.println(encontrado ? "SIM" : "NAO");
    }

    long fim = System.nanoTime();
    double tempo = (fim - inicio) / 1e6; // tempo em milissegundos

    // Criar o arquivo de log
    FileWriter fw = new FileWriter("matricula_sequencial.txt");
    fw.write("123456\t" + tempo + "\t" + comparacoes); // Substitua "123456" pela sua matrícula
    fw.close();

    sc.close();
}

}


/*
import java.util.BufferedReader;
import java.util.FileReader;
import java.util.Scanner;
import java.io.PrintWriter;

public class Sequencial{
    private String showId;
    private String title;

    public Sequencial(String showId, String title){
        this.showId = showId;
        this.title = title;
    }
    public Sequencial(){
        this("NaN", "NaN");
    }

    public void ler(String entrada, String path){
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
    }

    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        Sequencial s = new Sequencial();
        String entrada, e2;
        while(!(entrada = sc.nextLine()).equals("FIM")){ //entrada recebe o que esta sendo escrito, e testa se é igual a FIM. 
            Sequencial s2 = new Sequencial();
            ler(entrada, "/tmp/disneyplus.csv");
        }
        while(!(e2 = sc.nextLine().equals("FIM"))){


        }


        sc.close();
    }
}
*/