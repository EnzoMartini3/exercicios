class No{
    int tamanho = 255;
    char elemento;
    boolean folha;
    No[] prox;

    No(){
        this(' ');
    }

    No(char elemento){
        this.elemento = elemento;
        prox = new No[tamanho];
        for(int i=0;i<tamanho;i++) prox[i]=null;
        folha = false;
    }

    public static int hash(char x){
        return (int)x;
    }
}

//////////////////////////////////

class Trie {
    private No raiz;

    public Trie(){
        raiz = new No();
    }

    public boolean pesquisar(String s){
        return pesquisar(s, raiz, 0);
    }
    public boolean pesquisar(String s, No no, int i){
        boolean resp;
        if(no.prox[s.charAt(i)] == null){ //raiz nula
            resp = false;
        } else if(i == s.length() - 1){ //ativar folha
            resp = (no.prox[s.charAt(i)].folha == true);
        } else if(i < s.length() - 1){ //
            resp = pesquisar(s, no.prox[s.charAt(i)], i + 1);
        }
        return resp;
    }

    public void inserir(String s){
        raiz = inserir(s, raiz, 0);
    }
    public No inserir(String s, No no, int i){
        if(no.prox[s.charAt(i)]==null){
            no.prox[s.charAt(i)]==new No(charAt(i)); //se for nulo achamos o lugar

            if(i == s.length() - 1){ //se for o ultimo char fazemos folha==true
                no.prox[charAt(i)].folha=true;
            }else{ //se nao, recursao passando para proxima letra
                inserir(s, no.prox[charAt(i)], i+1); 
            }
        }else if(no.prox[charAt(i)].folha==false && i<s.length()-1){
            inserir(s, no.prox[charAt(i)], i+1); //se menor que o tamanho(nao sendo folha), recursao com a proxima letra
        }
    }

    public void mostrar(){
        mostrar("", raiz);
    }
    public void mostrar(String s, No no) {
        if(no.folha == true){
            System.out.println(s + no.elemento); //se for folha, imprime o elemento
        } else {
            for(int i = 0; i < no.prox.length; i++){
                if(no.prox[i] != null){
                    mostrar(s + no.elemento, no.prox[i]);
                }
            }
        }
    }

    public int contarAs(){
        int resp = 0;
        if(raiz != null){
            resp = contarAs(raiz);
        }
        return resp;
    }
    public int contarAs(No no) {
        int resp = (no.elemento == 'A') ? 1 : 0;

        if(no.folha == false){
            for(int i = 0; i < no.prox.length; i++){
                if(no.prox[i] != null){
                    resp += contarAs(no.prox[i]);
                }
            }
        }
        return resp;
    }
}