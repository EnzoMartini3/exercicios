import java.util.Scanner;

public class Inversao7 {
    public static String inversao(String frase) {
        StringBuilder frase2 = new StringBuilder(frase); //permite manipular a string
        return frase2.reverse().toString(); //metodo .reverse inverte a string
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String frase;
        do {
            frase = scanner.nextLine();
            if (!frase.equals("FIM")) { //evita que o programa inverta o "FIM"
                System.out.println(inversao(frase));
            }
        } while (!frase.equals("FIM")); //garante o fim do programa
        scanner.close();
    }
}
