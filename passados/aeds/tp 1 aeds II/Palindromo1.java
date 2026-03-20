import java.util.Scanner;

public class Palindromo1{
    // Função que verifica se é palíndromo
    public static boolean palindromo(String frase){
        int len=frase.length();
        int mlen=len/2; //garantir que a string compare-se ate a metade
        for (int i = 0; i < mlen; i++) {
            if (frase.charAt(i) != frase.charAt(len - i - 1)) {
                return false; //Não é palíndromo
            }
        }        
        return true; // É palíndromo
    }



public static void main(String[] args){
    Scanner scanner = new Scanner(System.in); //iniciar scanner
    String frase;
    do { 
        frase = scanner.nextLine(); //le a frase
        
        if((palindromo(frase)==true)){ //chama metodo, e em seguida imprime resultado
            System.out.println("SIM");
        }else{
            System.out.println("NAO");
        }
    } while (!(frase.equals("FIM")));
    scanner.close(); //fechar scanner
    }
}