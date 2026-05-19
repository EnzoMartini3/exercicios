import java.util.*;

class Livros{
    public static String vet[] = new String[200];
    public static int n = 0;

    public static void push(String nome){
        vet[n] = nome;
        n++;
    }
    
    public static void top(){
        if(n>0){
            System.out.println(vet[n-1]);
        }else{
            System.out.println("nada");
        }
    }

    public static String pop(){
        if(n>0){
            String ret = vet[n-1];
            vet[n-1] = "";
            n--;
            return ret;
        }else{
            System.out.println("Ai zoou meu");
            return "0";
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        String nome;
        
        while(!(entrada.equals("FIM"))){
            if(entrada.equals("POP")){
                System.out.println(pop());
            }else if(entrada.equals("TOP")){
                top();
            }else if(entrada.equals("PUSH")){
                nome = sc.nextLine(); 
                push(nome);
            }
            entrada = sc.nextLine();
        }

        sc.close();
    }
}