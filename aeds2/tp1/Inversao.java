import java.util.Scanner;

class Inversao {

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String inverte(String s, int i) { // Retorna o caractere atual + o restante da string invertida. quando o indice for menor que 0, paramos a recursao
        if (i < 0) {
            return "";
        }
        return s.charAt(i) + inverte(s, i - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            String entrada = sc.nextLine();
            while (!isFim(entrada)) {
                String resultado = inverte(entrada, entrada.length() - 1);
                System.out.println(resultado);
                if (sc.hasNextLine()) {
                    entrada = sc.nextLine();
                } else {
                    break;
                }
            }
        }
        sc.close();
    }
}