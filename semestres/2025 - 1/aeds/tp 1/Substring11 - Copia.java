import java.util.Scanner;

public class Substring11 {
    public static int comprimentoMaxSubstring(String s) {
        int maxLength = 0;
        int start = 0;
        boolean[] visto = new boolean[256]; // Array para marcar os caracteres já vistos

        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);

            // Se o caractere já foi visto, avançamos o "start" até removermos a repetição
            while (visto[c]) {
                visto[s.charAt(start)] = false;
                start++;
            }

            // Marcamos o caractere atual como visto
            visto[c] = true;
            
            // Atualizamos o comprimento máximo encontrado
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada;
        
        while (true) {
            entrada = scanner.nextLine();
            if (entrada.equals("FIM")) {
                break;
            }
            System.out.println(comprimentoMaxSubstring(entrada));
        }
        
        scanner.close();
    }
}
