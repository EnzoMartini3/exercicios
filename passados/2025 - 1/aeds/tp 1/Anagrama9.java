import java.util.Arrays;
import java.util.Scanner;

public class Anagrama9 {
    public static boolean anagrama(String frase1, String frase2) {
        //remove espacos e maiúsculas
        frase1 = frase1.toLowerCase().replaceAll("\\s+", "");
        frase2 = frase2.toLowerCase().replaceAll("\\s+", "");

        //se as strings tiverem tamanhos diferentes, nao sao anagramas
        if (frase1.length() != frase2.length()) {
            return false;
        }

        //string para array
        char[] arr1 = frase1.toCharArray();
        char[] arr2 = frase2.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
        // Compara os arrays ordenados
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            if (linha.equals("FIM")) break;

            String[] partes = linha.split(" - ");
            if (!(partes.length != 2)) break; // Se não forem duas palavras, ignora a entrada

            System.out.println(anagrama(partes[0], partes[1]) ? "SIM" : "NÃO");
        }

        scanner.close();
    }
}
