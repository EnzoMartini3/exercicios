import java.util.Scanner;

public class Palindromo16 {
    // Função que verifica se é um palíndromo
    public static boolean palindromo(String frase, int len, int i) {
        if (i >= len) { 
            return true; //se chegou na metade sem dar false, é palíndromo
        }
        if (frase.charAt(i) != frase.charAt(len)) {
            return false; //caracteres incopativels, não é palíndromo
        }
        return palindromo(frase, len - 1, i + 1); //aumenta/diminui as variaveis para que se aproximem e o codigo verifique ate a metade
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String frase;
        
        do { 
            frase = scanner.nextLine();
            if(!frase.equals("FIM")){ //impede que o FIM entre na verificacao
                if (palindromo(frase, frase.length()-1, 0)) { //a funcao retorna true se for palindromom, imprimindo o SIM ou false -> NAO caso nao
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO"); 
                }
            }
        } while (!frase.equals("FIM"));
        
        scanner.close(); // Fechar scanner
    }
}
