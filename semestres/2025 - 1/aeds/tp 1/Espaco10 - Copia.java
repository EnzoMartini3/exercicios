import java.util.Scanner;

public class Espaco10{
    // Função que verifica se é palíndromo
    public static void espacos(String frase){
        int len=frase.length();
        char c;
        int esp=1; //este procedimento conta os espaços da frase, porém é necessário somar 1, o que é feito iniciando a variavel em 1.
        for (int i = 0; i < len; i++) { //percorre a frase em busca de espaços
            c = frase.charAt(i); //c recebe o caracter em i
            if(c==(' ')){ //confere se é um espaço
                esp++;
            }
        }
        System.out.println(esp);
    }



public static void main(String[] args){
    Scanner scanner = new Scanner(System.in); //iniciar scanner
    String frase;
    do { 
        frase = scanner.nextLine(); //le a frase
        if(frase.equals("FIM")){
            break; //impede a leitura do FIM como palavra
        }
        espacos(frase);//chama metodo
    } while (!(frase.equals("FIM"))); //corre até o FIM
    scanner.close(); //fechar scanner
    }
}