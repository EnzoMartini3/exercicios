import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class QuicksortTop10 {
    static class Show {
        String show_id;
        String title;
        String type;
        String director;
        String cast;
        String country;
        String date_added;
        String release_year;
        String rating;
        String duration;
        String listedIn;

        LocalDate getDate() {
            try {
                if (date_added == null || date_added.equals("NaN") || date_added.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(date_added.trim(), DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH));
            } catch (DateTimeParseException e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return "=> " + show_id + " ## " + title + " ## " + type + " ## " + director + " ## " +
                   "[" + cast + "] ## " + country + " ## " + date_added + " ## " + release_year + " ## " +
                   rating + " ## " + duration + " ## " + "[" + listedIn + "] ##";
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/tmp/data.csv"));
        List<Show> list = new ArrayList<>();

        // Ignora o cabeçalho
        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] fields = line.split("(?<!\\\"),(?!\\\")", -1); // CSV robusto

            if (fields.length < 12) continue;

            Show show = new Show();
            show.show_id = fields[0].trim();
            show.title = fields[2].trim();
            show.type = fields[1].trim();
            show.director = fields[3].trim();
            show.cast = fields[4].trim();
            show.country = fields[5].trim();
            show.date_added = fields[6].trim();
            show.release_year = fields[7].trim();
            show.rating = fields[8].trim();
            show.duration = fields[9].trim();
            show.listedIn = fields[10].trim();

            if (!"Movie".equalsIgnoreCase(show.type)) continue;
            if (show.title == null || show.title.isEmpty()) continue;

            LocalDate date = show.getDate();
            if (date == null) continue;

            list.add(show);
        }

        br.close();

        // Quicksort personalizado
        quicksort(list, 0, list.size() - 1);

        // Imprime os 10 primeiros
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            System.out.println(list.get(i));
        }
    }

    static void quicksort(List<Show> list, int left, int right) {
        int i = left, j = right;
        Show pivot = list.get((left + right) / 2);

        while (i <= j) {
            while (compare(list.get(i), pivot) < 0) i++;
            while (compare(list.get(j), pivot) > 0) j--;

            if (i <= j) {
                Collections.swap(list, i, j);
                i++;
                j--;
            }
        }

        if (left < j) quicksort(list, left, j);
        if (i < right) quicksort(list, i, right);
    }

    static int compare(Show a, Show b) {
        int cmp = b.getDate().compareTo(a.getDate()); // decrescente
        if (cmp != 0) return cmp;
        return a.title.compareToIgnoreCase(b.title); // crescente
    }
}
