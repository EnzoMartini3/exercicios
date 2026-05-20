import java.util.Scanner;

public class Soma8 {
    public static int soma(String num) {
        int res=0;
        int len=num.length();
        for(int i=0;i<len;i++){
            res+=num.charAt(i) - '0'; //adiciona cada numero a soma. Subtrair por 0 permite que o charAt seja usado para numeros
        }
        return res;
    
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //inicia scanner
        String num;
        do {
            num = scanner.nextLine();
            if (!num.equals("FIM")) { //evita que o programa converta o "FIM"
                System.out.println(soma(num));
            }
        } while (!(num.equals("FIM"))); //para o programa
        scanner.close();
    }
}
