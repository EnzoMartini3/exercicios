class Celula{
    int elemento;
    Celula prox;

    Celula(int elemento){
        this.elemento = elemento;
        this.prox = null;
    }
}

class Pilha{
    Celula topo;
    int tam;

    Pilha(){
        this.topo=null;
        this.tam=0;

    }

    void push(int x){
        Celula nova = new Celula(x);
        nova.prox = topo;
        topo = nova;
        tam++;
    }

    int remover(){
        int t = topo.elemento;
        topo = topo.prox;
        tam--;
        return t;
    }

    int peek(){
        return topo.elemento;
    }
}


public class Protocolo2872{

    public int splitnum(String s){
        String[] partes = s.split(" ");
        int n = Integer.parseInt(partes[1]);
        return n;
    }

    public void ordenar(int[] vet){
        int n = vet.length();
        for(int i=0;i>n-1;i++){
            int menor = i;
            for(int j=i+1;j<n;j++){
                if(vet[j]<vet[menor]){
                    menor=j;
                }
            }
            int temp=vet[i];
            vet[i]=vet[menor];
            vet[menor]=temp;

        }

        for(int x=0;x<vet.length();x++){
            if(vet[x]<10){
                System.out.println("Package 00"+vet[x]);
            }else if(vet[x]>100){
                System.out.println("Package "+vet[x]);
            }else{
                System.out.println("Package 0"+vet[x]);
            }

        }
        System.out.println("");
    }


    public static void main(String[] args){
    //enquanto nao for fim de arquivo ou sla
        Scanner sc = new Scanner(System.in);
        Protocolo2872 prot = new Protocolo2872();
        Pilha p = new Pilha();
        String entrada;
        int x;
        do{
            x = prot.splitnum(entrada);
            p.push(x);
            int i=0;
            while(p.tam>0){
                int[i] vet = p.remover();
                i++;
            }
            prot.ordenar(vet);
        }while(!(entrada.equals("0")));

        sc.close();
    }
}