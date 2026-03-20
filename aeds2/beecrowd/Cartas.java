import java.util.*;

class Cartas{
    public static int[] pilha = new int[200];
    public static int[] descarte = new int[200];
    public static int descTam = 0;
    public static int n = 0;

    public static void imprimeDescarte(){
        System.out.print("Discarded cards: " + descarte[0]);
        for(int j=1; j<descTam;j++){
            System.out.print(", " + descarte[j]);
        }
    }

    public static int desempilhar(){
        int ret = pilha[n];
        pilha[n] = 0;
        n--;
        return ret;
    }

    public static void empilhar(int num){
        pilha[n] = num;
        n++;
    }

    public static int cartadas(int cartas){
        int cartaFinal;
        for(int i=cartas; i>0; i--){
            empilhar(i+1);
        }
        for(int j=0; j<cartas-1;j++){
            descarte[j] = desempilhar();
            descTam++;
        }
        cartaFinal = pilha[n];

        return cartaFinal;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cartas = sc.nextInt();
        while(cartas > 0){
            int cartaFinal = cartadas(cartas);
            imprimeDescarte();
            System.out.println("Remaning card: " + cartaFinal);
            cartas = sc.nextInt();
        }

        sc.close();
    }
}