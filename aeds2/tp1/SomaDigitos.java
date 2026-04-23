import java.util.Scanner;

class SomaDigitos {

    //pegamos o resto da divisao por 10 (o ultimo digito) e somamos com a chamada recursiva do numero dividido por 10 (o restante do numero).

    public static int somador(int num) {
        if (num == 0) {
            return 0;
        }
        return (num % 10) + somador(num / 10);// (num % 10) isola o ultimo digito, (num / 10) remove o ultimo digito
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int num = sc.nextInt();            
            System.out.println(somador(num));
        }

        sc.close();
    }
}