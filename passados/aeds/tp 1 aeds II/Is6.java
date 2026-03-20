import java.util.Scanner;

public class Is6 {
    public static boolean soVogais(String s) {
        return s.matches("[AEIOUaeiou]+"); //diretamente verifica se existe algum desses caracteres na string
    }
    public static boolean soConsoantes(String s) {
        return s.matches("[BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz]+");
    }
    public static boolean ehInteiro(String s) {
        return s.matches("-?\\d+");
    }
    public static boolean ehReal(String s) {
        return s.matches("-?\\d*[.,]?\\d+");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //inicia scanner
        String entrada;

        while (!(entrada = scanner.nextLine()).equals("FIM")) {
            System.out.printf("%s %s %s %s%n", //imprime 4 variaveis que caso verdadeiras serao SIM
                soVogais(entrada) ? "SIM" : "NAO",
                soConsoantes(entrada) ? "SIM" : "NAO",
                ehInteiro(entrada) ? "SIM" : "NAO",
                ehReal(entrada) ? "SIM" : "NAO"
            );
        }
        scanner.close();
    }
}
