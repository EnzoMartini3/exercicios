import java.util.Scanner;
import java.io.*;

public class Senha12{
    // Função que verifica se é palíndromo
    public static boolean senha(String frase){
        int len=frase.length();
        if(len<8){
            return false; //senha tem 7 ou menos caracteres
        }
                if(!(frase.matches(".*[0-9].*"))){
                    return false; //senha sem numeros
                }if(!(frase.matches(".*[A-Z].*"))){
                    return false; //senha sem maisculas
                }if(!(frase.matches(".*[a-z].*"))){
                    return false; //senha sem minusculas
                }if(!(frase.matches(".*[!@#$%&*^].*"))){
                    return false; //senha sem caracter especial
                }
        return true; //senha valida
    }



public static void main(String[] args) throws UnsupportedEncodingException{
    Scanner scanner = new Scanner(System.in, "UTF-8"); //iniciar scanner
    System.setOut(new PrintStream(System.out, true, "UTF-8"));
    String frase;
    do { 
        frase = scanner.nextLine(); //le a frase
        if(frase.equals("FIM")){
            break;
        }
        if((senha(frase)==true)){ //chama metodo, e em seguida imprime resultado
            System.out.println("SIM");
        }else{

            System.out.println("NAO");
        }
    } while (!(frase.equals("FIM")));
    scanner.close(); //fechar scanner
    }
}