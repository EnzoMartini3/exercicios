class Is3{

    public static boolean isVogal(String s){
        boolean fato = true;
        int n = s.length();
        for(int i=0; i<n; i++){ //tem alguma não-vogal? falso nele.
            if(s.charAt(i) != 'a' || 'e' || 'i' || 'o' || 'u' || 'A' || 'E' || 'I' || 'O' || 'U'){
                fato = false;
            }
        }
        return fato;
    }

    public static boolean isConsoante(String s){
        boolean fato = true;
        int n = s.length();
        for(int i=0; i<n; i++){ //tem alguma vogal? falso nele.
            if(s.charAt(i) == 'a' || 'e' || 'i' || 'o' || 'u' || 'A' || 'E' || 'I' || 'O' || 'U'){
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
        String saida = "";
        while(!(entrada.equals("FIM"))){
            if(isVogal(entrada)){
                saida = saida + "X1 ";
            }else if(isConsoante(entrada)){
                saida = saida + "X2 ";
            }else if(isInt(entrada)){
                saida = saida + "X3 ";
            }else if(isReal(entrada)){
                saida = saida + "X4";
            }
            System.out.println(saida);
            entrada = sc.nextLine();
        }
        sc.close();
    }
}