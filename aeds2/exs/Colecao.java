import java.util.*;
import java.io.*;

class Item{
    private int id;
    private String nome;
    private String raridade;
    private int preco;
    private boolean equipavel;
    private String[] atributos;

    public Item(int id, String nome, String raridade, int preco, boolean equipavel, String[] atributos){
        this.id = id;
        this.nome = nome;
        this.raridade = raridade;
        this.preco = preco;
        this.equipavel = equipavel;
        this.atributos = atributos;
    }

    public static Item parseItem(String s){
        Scanner sc = new Scanner(s);
        sc.useDelimiter(",");
        int id = sc.nextInt();
        String nome = sc.next();
        String raridade = sc.next();
        int preco = parsePreco(sc.next());
        boolean equipavel = parseEquipavel(sc.next());

        sc.useDelimiter(";");
        String[] atributos = new String[300];
        int i=0;
        while(sc.hasNext()){
            atributos[i] = sc.next();
            i++;
        }
        sc.close();
        return new Item(id, nome, raridade, preco, equipavel, atributos);
    }

    public static boolean parseEquipavel(String s){
        if(s.equals("true")){
            return true;
        }
        return false;
    }

    public static int parsePreco(String s){
        int i = 0;
        int ret = 0;
        char c = s.charAt(i);
        while(c >= 0 && c<10){
            ret = ret*10;
            ret += (int) c;
            i++;
            c = s.charAt(i);
        }
        return ret;
    }

    public int getId(){
        return this.id;
    }

    public static String formatar(Item este){

        return String.format(""
        
        
        
        );
    }
}

class Inventario{
    private Item[] itens;
    private int tamanho;

    public Inventario(Item[] itens, int tamanho){
        this.itens = itens;
        this.tamanho = tamanho;
    }

    public Item getItemById(int id){
        for(int i=0; i<tamanho; i++){
            if(itens[i].getId() == id){
                return itens[i];
            }
        }
        return null;
    }

    public static Inventario lerCsv(){
        Item[] itens = new Item[600];
        int tamanho = 0;

        try{
            Scanner sc = new Scanner(new File("itens.csv"));
            String cabecalho = sc.nextLine();
            while(sc.hasNextLine()){
                String s = sc.nextLine();
                Item este = Item.parseItem(s);
                itens[tamanho] = este;
                tamanho++;
            }
            sc.close();
        } catch(Exception e){}

        return new Inventario(itens, tamanho);
    }
}

class Colecao{
    public static void main(String[] args){
        Inventario inv = Inventario.lerCsv();
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        while(!(entrada.equals("fim"))){
            Item este = inv.getItemById(entrada);
            System.out.println(este.formatar(este));
            entrada = sc.nextLine();
        }

        sc.close();
    }
}