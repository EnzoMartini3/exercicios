import java.util.Scanner;

public class Cesar3 {
    public static String deslocar(String frased, int chave) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < frased.length(); i++) {
            char caractere = frased.charAt(i);

            //desloca para caracteres entre 32 e 126(letras, acentos, etc)
            if (caractere >= 32 && caractere <= 126) {
                caractere = (char) (32 + (caractere - 32 + chave) % (126 - 32 + 1));
            } //se ultrapassar o 126, ele retorna para 32

            resultado.append(caractere);
        }    
        return resultado.toString();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int chave = 3;

        while (true) {
            String frase = scanner.nextLine();

            if (frase.equals("FIM")) {
                break;
            }

            System.out.println(deslocar(frase, chave)); //imprime
        }

        scanner.close();//fecha scanner
    }
}
