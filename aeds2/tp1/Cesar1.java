import java.util.*;

class Cesar1{
    public static void cesar(String s){ //metodo iterativo que recebe a string, cria uma vazia e percorre ela toda. a cada iteracao, pegamos o char da posicao, pulamos 3 letras/simbolos, convertemos para char e depois juntamos a uma nova string.
        String nova = "";
        for(int i=0; i < s.length(); i++){
            char c = s.charAt(i);
            System.out.print((char)(c + 3));
        }
        System.out.println();
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        while(!(entrada.equals("FIM"))){
            cesar(entrada);
            entrada = sc.nextLine();        
        }
        sc.close();
    }
}