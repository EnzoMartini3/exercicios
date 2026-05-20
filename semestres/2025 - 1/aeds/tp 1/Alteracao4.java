import java.util.Random;
import java.util.Scanner;

public class Alteracao4{
    public static String altera(String frase){
        Random gerador = new Random();
        gerador.setSeed(4);
        char lrand,lset=4;
        lrand = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        int len = frase.length();
        for(int i=0;i<len;i++){

        }

        return frase.replace(lrand, lset);
    }
    
    public static void main(String[] args){
        String frase;
        Scanner scanner = new Scanner(System.in);

        do { 
            frase = scanner.nextLine();
            System.out.println(altera(frase));
        } while (!(frase.equals("FIM")));

        scanner.close();
    }
}