
import java.util.Scanner;


public class Is20 {
    public static void is(String s, int c) {
        if (c == 0) { //percebi no final que o switchcase provavelmente seria melhor...
            System.out.print(s.matches("[AEIOUaeiou]+") ? "SIM " : "NAO ");
            is(s, c + 1); //soma 1 para ativar a proxima verficiacao
        } else if (c == 1) { 
            System.out.print(s.matches("[BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz]+") ? "SIM " : "NAO ");
            is(s, c + 1);
        } else if (c == 2) { 
            System.out.print(s.matches("-?\\d+") ? "SIM " : "NAO ");
            is(s, c + 1);
        } else if (c == 3) { 
            System.out.println(s.matches("-?\\d+(\\.\\d+)?") ? "SIM" : "NAO"); // Última verificação quebra a linha
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada;

        while (!(entrada = scanner.nextLine()).equals("FIM")) {
            is(entrada, 0); //inicia com um 0 para contagem
        }
        scanner.close();
    }
}
