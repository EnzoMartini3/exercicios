import java.io.*;
import java.util.*;

class Date {
    int day, month, year;

    public Date() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    public Date(int d, int m, int y) {
        this.day = d;
        this.month = m;
        this.year = y;
    }
}

class Show {
    String showId = "NaN";
    String type = "NaN";
    String title = "NaN";
    String director = "NaN";
    List<String> cast = new ArrayList<>();
    String country = "NaN";
    String dateAdded = "NaN";
    int releaseYear = 0;
    String rating = "NaN";
    String duration = "NaN";
    List<String> listedIn = new ArrayList<>();
}
class Node {
    Show data;
    Node prev, next;

    Node(Show s) {
        data = s;
    }
}

class DoublyLinkedList {
    Node head, tail;

    void append(Show s) {
        Node newNode = new Node(s);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    int size() {
        int count = 0;
        for (Node cur = head; cur != null; cur = cur.next) count++;
        return count;
    }

    Show getAt(int index) {
        Node cur = head;
        for (int i = 0; i < index && cur != null; i++) {
            cur = cur.next;
        }
        return cur != null ? cur.data : null;
    }

    void setAt(int index, Show s) {
        Node cur = head;
        for (int i = 0; i < index && cur != null; i++) {
            cur = cur.next;
        }
        if (cur != null) cur.data = s;
    }
}
class Util {
    static long comparacoes = 0;

    static int getMonthNumber(String month) {
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        for (int i = 0; i < 12; i++) {
            if (month.equalsIgnoreCase(months[i])) return i + 1;
        }
        return 0;
    }

    static Date parseDate(String dateStr) {
        if (dateStr.equals("NaN")) return new Date();
        try {
            String[] parts = dateStr.split(" ");
            int day = Integer.parseInt(parts[1].replace(",", ""));
            int month = getMonthNumber(parts[0]);
            int year = Integer.parseInt(parts[2]);
            return new Date(day, month, year);
        } catch (Exception e) {
            return new Date();
        }
    }

    static int compareShows(Show a, Show b) {
        comparacoes++;
        Date da = parseDate(a.dateAdded);
        Date db = parseDate(b.dateAdded);

        if (da.year != db.year) return da.year - db.year;
        if (da.month != db.month) return da.month - db.month;
        if (da.day != db.day) return da.day - db.day;
        return a.title.compareTo(b.title);
    }

    static void quicksort(DoublyLinkedList list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quicksort(list, low, pi - 1);
            quicksort(list, pi + 1, high);
        }
    }

    static void swap(DoublyLinkedList list, int i, int j) {
        Show temp = list.getAt(i);
        list.setAt(i, list.getAt(j));
        list.setAt(j, temp);
    }

    static int partition(DoublyLinkedList list, int low, int high) {
        Show pivot = list.getAt(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compareShows(list.getAt(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

static void removeQuotesAndTrim(StringBuilder sb) {
    if (sb.length() == 0) {
        sb.append("NaN");
        return;
    }

    if (sb.charAt(0) == '"') {
        sb.deleteCharAt(0);
    }

    if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '"') {
        sb.deleteCharAt(sb.length() - 1);
    }

    while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
        sb.deleteCharAt(0);
    }

    while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
        sb.deleteCharAt(sb.length() - 1);
    }

    if (sb.length() == 0) {
        sb.append("NaN");
    }
}


    static List<String> splitAndSort(String s) {
        List<String> list = new ArrayList<>();
        if (s.equals("NaN")) return list;
        String[] parts = s.split(",");
        for (String p : parts) {
            StringBuilder sb = new StringBuilder(p);
            removeQuotesAndTrim(sb);
            list.add(sb.toString());
        }
        Collections.sort(list);
        return list;
    }

    static Show parseShow(String line) {
        String[] tokens = new String[20];
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString());

        Show s = new Show();
        if (fields.size() > 0) s.showId = fields.get(0);
        if (fields.size() > 1) s.type = fields.get(1);
        if (fields.size() > 2) s.title = fields.get(2);
        if (fields.size() > 3) s.director = fields.get(3);
        if (fields.size() > 4) s.cast = splitAndSort(fields.get(4));
        if (fields.size() > 5) s.country = fields.get(5);
        if (fields.size() > 6) s.dateAdded = fields.get(6);
        if (fields.size() > 7) s.releaseYear = parseInt(fields.get(7));
        if (fields.size() > 8) s.rating = fields.get(8);
        if (fields.size() > 9) s.duration = fields.get(9);
        if (fields.size() > 10) s.listedIn = splitAndSort(fields.get(10));

        return s;
    }

    static int parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}

public class QuicksortLD {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        DoublyLinkedList list = new DoublyLinkedList();

        while (true) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;

            BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
            String linha = br.readLine(); // skip header

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith(entrada + ",")) {
                    Show s = Util.parseShow(linha);
                    list.append(s);
                    break;
                }
            }
            br.close();
        }

        long start = System.currentTimeMillis();
        Util.quicksort(list, 0, list.size() - 1);
        long end = System.currentTimeMillis();

        PrintWriter pw = new PrintWriter("matricula_quicksort3.txt");
        pw.printf("Matricula: 850602\tTempo: %.6f\tComparacoes: %d\n",
                (end - start) / 1000.0, Util.comparacoes);
        pw.close();

        for (Node cur = list.head; cur != null; cur = cur.next) {
            Show s = cur.data;
System.out.printf("=> %s ## %s ## %s ## %s ## [", s.showId, s.title, s.type, s.director);
for (int j = 0; j < s.cast.size(); j++) {
    System.out.print(s.cast.get(j));
    if (j < s.cast.size() - 1) System.out.print(", ");
}
System.out.printf("] ## %s ## %s ## %d ## %s ## %s ## [", s.country, s.dateAdded, s.releaseYear, s.rating, s.duration);
for (int j = 0; j < s.listedIn.size(); j++) {
    System.out.print(s.listedIn.get(j));
    if (j < s.listedIn.size() - 1) System.out.print(", ");
}
System.out.println("] ##");
            System.out.println(s.listedIn.toString() + " ##");
        }
    }
}
