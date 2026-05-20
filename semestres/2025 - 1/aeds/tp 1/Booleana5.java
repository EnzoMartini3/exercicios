import java.util.*;

public class Booleana5 {
    public static int avaliarExpressao(String entrada) {
        String[] partes = entrada.split(" ");
        int n = Integer.parseInt(partes[0]);
        int[] valores = new int[n];
        
        // Atribui os valores das variáveis A, B, C, etc.
        for (int i = 0; i < n; i++) {
            valores[i] = Integer.parseInt(partes[i + 1]);
        }
        
        StringBuilder expressaoBuilder = new StringBuilder();
        for (int i = n + 1; i < partes.length; i++) {
            expressaoBuilder.append(partes[i]).append(" ");
        }
        String expressao = expressaoBuilder.toString().trim();
        
        // Substitui as variáveis pela sua respectiva avaliação booleana
        for (int i = 0; i < n; i++) {
            char var = (char) ('A' + i);
            expressao = expressao.replace("(" + var + ")", Integer.toString(valores[i]));
            expressao = expressao.replace(Character.toString(var), Integer.toString(valores[i]));
        }
        
        return avaliar(expressao) ? 1 : 0;
    }
    
    private static boolean avaliar(String expressao) {
        expressao = expressao.replaceAll("[(),]", " ").trim();  // Remove parênteses e vírgulas
        String[] tokens = expressao.split("\\s+");  // Divide a expressão por espaços
        Stack<Boolean> pilha = new Stack<>();
        
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i].trim();
            
            if (token.equals("not")) {
                if (!pilha.isEmpty()) {
                    boolean valor = pilha.pop();
                    pilha.push(!valor);  // Aplica o operador NOT
                }
            } else if (token.equals("and")) {
                if (pilha.size() >= 2) {
                    boolean valor1 = pilha.pop();
                    boolean valor2 = pilha.pop();
                    pilha.push(valor1 && valor2);  // Aplica o operador AND
                }
            } else if (token.equals("or")) {
                if (pilha.size() >= 2) {
                    boolean valor1 = pilha.pop();
                    boolean valor2 = pilha.pop();
                    pilha.push(valor1 || valor2);  // Aplica o operador OR
                }
            } else if (token.equals("1") || token.equals("0")) {
                pilha.push(token.equals("1"));  // Converte para valor booleano
            }
        }
        
        // Retorna o valor final da expressão
        return !pilha.isEmpty() && pilha.pop();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) break;
            System.out.println(avaliarExpressao(entrada));  // Avalia a expressão
        }
        scanner.close();
    }
}
