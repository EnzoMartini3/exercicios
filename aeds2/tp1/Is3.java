import java.util.Scanner;
class Is3{

    public static boolean isVogal(String s){
        boolean fato = true;
        int n = s.length();
        for(int i=0; i<n; i++){ //tem alguma não-vogal? falso nele.
            char c = s.charAt(i);
            if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || 
              c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')) {
                fato = false;
            }
        }
        return fato;
    }

    public static boolean isConsoante(String s){
        boolean fato = true;
        int n = s.length();
        for(int i=0; i<n; i++){ //tem alguma vogal? falso nele.
            char c = s.charAt(i);
            if ((c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || 
             c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')) {
                            fato = false;
            }
        }
        return fato;
    }

    public static boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    } 

    public static boolean isReal(String s) {
        try {
            Double.parseDouble(s.replace(',', '.'));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args){ //para cada entrada, testamos todos os métodos e concatenamos as respostas em uma segunda string para cada resultado verdadeiro
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        while(!(entrada.equals("FIM"))){
            String saida = "";
                if (isVogal(entrada)) {
                    saida += "SIM ";
                } else {
                    saida += "NAO ";
                }
                if (isConsoante(entrada)) {
                    saida += "SIM ";
                } else {
                    saida += "NAO ";
                }
                if (isInt(entrada)) {
                    saida += "SIM ";
                } else {
                    saida += "NAO ";
                }
                if (isReal(entrada)) {
                    saida += "SIM";
                } else {
                    saida += "NAO";
                }
            System.out.println(saida);
            entrada = sc.nextLine();
        }
        sc.close();
    }
}