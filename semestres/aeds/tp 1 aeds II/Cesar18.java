import java.util.Scanner;

public class Cesar18 {
    public static String deslocar(String frase, int chave) {
        return deslocarRec(frase, chave, 0);
    }

    private static String deslocarRec(String frase, int chave, int i) {
        if (i >= frase.length()) { // Caso base: quando i atinge o tamanho da string
            return "";
        }

        char caractere = frase.charAt(i);
        if (caractere >= 32 && caractere <= 126) {
            caractere = (char) (32 + (caractere - 32 + chave) % (126 - 32 + 1));
        }

        return caractere + deslocarRec(frase, chave, i + 1); // Concatena o caractere atual com o resto da string
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int chave = 3;

        while (true) {
            String frase = scanner.nextLine();
            if (frase.equals("FIM")) {
                break;
            }
            System.out.println(deslocar(frase, chave)); // Imprime a string cifrada
        }
        scanner.close(); // Fecha o scanner
    }
}
