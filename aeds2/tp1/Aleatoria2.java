import java.util.*;

class Aleatoria2{
    public static Random gerador = new Random(); //precisa declarar fora pra nao randomizar toda vez. tudo na classe tambem precisa ser public static( aparentemente)

    public static String randomizar(String s){ //declaramos 2 variáveis, a letra a ser substituída e a substituta. Então criamos um vetor de char que vai "imitando" a string original, caso achemos a letra l1 trocamos ela por l2, caso contrário vamos "construíndo" uma nova string e depois oficializamos com o new String
        char l1 = ((char)('a' + (Math.abs(gerador.nextInt()))%26));
        char l2 = ((char)('a' + (Math.abs(gerador.nextInt()))%26));
        int n = s.length();
        char[] nova = new char[(n)];

        for(int i=0; i<n; i++){
            if(s.charAt(i) == l1){
                nova[i] = l2;
            }else{
                nova[i] = s.charAt(i);
            }
        }
        String retorno = new String(nova);

        return retorno;
    }

    public static void main(String[] args){
        gerador.setSeed(4); //seed definida
        Scanner sc = new Scanner(System.in); //o resto do main não foge muito do padrão
        String entrada = sc.nextLine();
        while(!(entrada.equals("FIM"))){
            System.out.println(randomizar(entrada));
            entrada = sc.nextLine();
        }
        sc.close();
    }
}