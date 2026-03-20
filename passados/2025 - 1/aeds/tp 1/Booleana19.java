import java.util.*;

public class Booleana19 {
    public static int avaliarExpressao(String entrada) {
        String[] partes = entrada.split(" ", 2);
        if (partes.length < 2) {
            throw new IllegalArgumentException("Entrada inválida");
        }
        
        int n = Integer.parseInt(partes[0]);
        String[] valores = partes[1].split(" ", n + 1);
        if (valores.length < n + 1) {
            throw new IllegalArgumentException("Valores insuficientes na entrada");
        }
        
        String expressao = valores[n].trim();
        Map<Character, Boolean> variaveis = new HashMap<>();
        for (int i = 0; i < n; i++) {
            variaveis.put((char) ('A' + i), valores[i].equals("1"));
        }
        
        return avaliar(expressao, variaveis) ? 1 : 0;
    }
    
    private static boolean avaliar(String expressao, Map<Character, Boolean> variaveis) {
        return avaliarRecursivo(tokenizar(expressao), new int[]{0}, variaveis);
    }
    
    private static List<String> tokenizar(String expressao) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (char c : expressao.toCharArray()) {
            if (c == '(' || c == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (c == ' ') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(c);
            }
        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }
        return tokens;
    }
    
    private static boolean avaliarRecursivo(List<String> tokens, int[] index, Map<Character, Boolean> variaveis) {
        if (index[0] >= tokens.size()) {
            throw new IllegalArgumentException("Expressão mal formatada");
        }
        
        String token = tokens.get(index[0]++);
        
        if (token.equals("(")) {
            if (index[0] >= tokens.size()) {
                throw new IllegalArgumentException("Expressão mal formatada");
            }
            String operador = tokens.get(index[0]++);
            boolean resultado;
            if (operador.equals("not")) {
                resultado = !avaliarRecursivo(tokens, index, variaveis);
            } else {
                boolean valor1 = avaliarRecursivo(tokens, index, variaveis);
                boolean valor2 = avaliarRecursivo(tokens, index, variaveis);
                resultado = operador.equals("and") ? (valor1 && valor2) : (valor1 || valor2);
            }
            if (index[0] >= tokens.size() || !tokens.get(index[0]).equals(")")) {
                throw new IllegalArgumentException("Parênteses desbalanceados");
            }
            index[0]++; // Avançar sobre ")"
            return resultado;
        } else if (token.equals("1")) {
            return true;
        } else if (token.equals("0")) {
            return false;
        } else {
            return variaveis.getOrDefault(token.charAt(0), false);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) break;
            try {
                System.out.println(avaliarExpressao(entrada));
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close();
    }
}