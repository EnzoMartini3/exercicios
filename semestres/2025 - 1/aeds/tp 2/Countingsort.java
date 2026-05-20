import java.util.*;
import java.io.*;

public class Countingsort implements Cloneable {
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
    static int comp = 0, mov = 0;

    public Countingsort() {
        this("NaN", "NaN", "NaN", "NaN", new String[0], "NaN", "NaN", 0, "NaN", "NaN", new String[0]);
    }

    public Countingsort(String showId, String type, String title, String director, String[] cast, String country, 
                       String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
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

    public static void countingsort(ArrayList<Countingsort> lista) {
        if (lista.isEmpty()) return;

        // Encontrar o ano mínimo e máximo
        int minYear = lista.get(0).getReleaseYear();
        int maxYear = lista.get(0).getReleaseYear();
        for (Countingsort show : lista) {
            if (show.getReleaseYear() < minYear) minYear = show.getReleaseYear();
            if (show.getReleaseYear() > maxYear) maxYear = show.getReleaseYear();
            comp += 2;
        }

        int range = maxYear - minYear + 1;
        ArrayList<ArrayList<Countingsort>> count = new ArrayList<>(range);
        
        // Inicializar listas
        for (int i = 0; i < range; i++) {
            count.add(new ArrayList<>());
        }

        // Contagem dos elementos
        for (Countingsort show : lista) {
            count.get(show.getReleaseYear() - minYear).add(show);
            mov++;
        }

        // Ordenar cada lista pelo título (critério de desempate)
        for (ArrayList<Countingsort> yearList : count) {
            if (yearList.size() > 1) {
                yearList.sort((a, b) -> {
                    comp++;
                    return a.getTitle().compareTo(b.getTitle());
                });
                mov += yearList.size() * (int)(Math.log(yearList.size()) / Math.log(2)); // Estimativa para ordenação interna
            }
        }

        // Reconstruir a lista original
        int index = 0;
        for (ArrayList<Countingsort> yearList : count) {
            for (Countingsort show : yearList) {
                lista.set(index++, show);
                mov++;
            }
        }
    }

    //getset
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

    public static void criaLog(int mat, double tempo, int comp, int mov) {
        try {
            FileWriter fw = new FileWriter("matricula_countingsort.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.write(mat + "\t" + tempo + "\t" + comp + "\t" + mov);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Countingsort clone() {
        return new Countingsort(showId, type, title, director, cast.clone(), country, 
                              dateAdded, releaseYear, rating, duration, listedIn.clone());
    }

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

    public void ler(String targetId, String pathCSV) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Countingsort> lista = new ArrayList<>();
        String path = "/tmp/disneyplus.csv";
        String entrada;
        long inicio = System.nanoTime();

        while (!(entrada = sc.nextLine()).equals("FIM")) {
            Countingsort s = new Countingsort();
            s.ler(entrada, path);
            lista.add(s.clone());
        }

        countingsort(lista);
        for (Countingsort s : lista) {
            s.imprimir();
        }
        
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1e6;
        criaLog(850602, tempo, comp, mov);

        sc.close();
    }
}