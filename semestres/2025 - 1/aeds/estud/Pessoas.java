import java.util.*;
import java.time.LocalDate;

class Pessoas{
    String nome;
    LocalDate nasc;

    public Pessoas(){
        this.nome = "";
        this.nasc = LocalDate.now;
    }

    public Pessoas(String nome, int dia, int mes, int ano){
        this.nome = nome;
        this.nasc = LocalDate.of(ano, mes, dia);
    }

    public void mostrar(){
        System.out.println(nome+""+nasc.toString+"");
    }

    public int hash(){
        return hash(nasc.Month, nasc.Day);
    }

    public int hash(int mes, int dia) {
        int var = 0;
            if (mes == 1) var = dia - 1;
            else if (mes == 2) var = 31 + dia - 1;
            else if (mes == 3) var = 60 + dia - 1;
            else if (mes == 4) var = 91 + dia - 1;
            else if (mes == 5) var = 121 + dia - 1;
            else if (mes == 6) var = 152 + dia - 1;
            else if (mes == 7) var = 182 + dia - 1;
            else if (mes == 8) var = 213 + dia - 1;
            else if (mes == 9) var = 244 + dia - 1;
            else if (mes == 10) var = 274 + dia - 1;
            else if (mes == 11) var = 305 + dia - 1;
            else if (mes == 12) var = 335 + dia - 1;
        return var;
    }

    public static void main(String[] args){
        int n;
        Pessoas p = new Pessoas;
        Scanner sc = new Scanner(System.in);
        do{
            n = sc.nextInt();
            switch(n){
                case 1:
                    //adicionar os trem ai
                break;
            }
        }while(i!=5);


    }
}