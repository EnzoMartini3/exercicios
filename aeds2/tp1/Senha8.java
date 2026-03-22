import java.util.*;

class Senha8{
    public static boolean senhaValida(String s){
        boolean valido1 = false;
        boolean valido2 = false;
        boolean valido3 = false;
        boolean valido4 = false;
        boolean valido5 = false;
        int tam = s.length();

        if(tam >= 8){ //passo 1: tamanho
            valido1 = true;
        }

        for(int i=0; i<tam; i++){ //passo 2: maiscula
            if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'){
                valido2 = true;
                break;
            }
        }

        for(int i=0; i<tam; i++){ //passo 3: minuscula
            if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z'){
                valido3 = true;
                break;
            }
        }

        for(int i=0; i<tam; i++){ //passo 4: num
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                valido4 = true;
                break;
            }
        }

        for(int i=0; i<tam; i++){ //passo 5: simbolo
            if(!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') && !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z') && !(s.charAt(i) >= '0' && s.charAt(i) <= '9') && (s.charAt(i) != ' ' && s.charAt(i) != '\n')){
                valido5 = true;
                break;
            }
        }

        if((valido1 == true) && (valido2 == true) && (valido3 == true) && (valido4 == true) && (valido5 == true)){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        while(!(entrada.equals("FIM"))){
            if(senhaValida(entrada)){
                System.out.println("SIM");
            }else{
                System.out.println("NAO");
            }
            entrada = sc.nextLine();
        }
        sc.close();
    }
}