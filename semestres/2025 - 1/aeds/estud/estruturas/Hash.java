// gerenciamento de colisoes:
// 1. Hash direta com área de reserva (overflow)
// 2. Hash direta com rehash
// 3. Hash indireta com lista flexivel simples
// 4. Estrutura hibrida

class Hash{
    public Hash(int x){
        return x
    }

    int hash(int x){
        return x % tamTabela;
    }

    int rehash(int x){
        return ++x % tamTabela;
    }

    void inserir(int x){
        int index = hash[x]
        if(pesquisar(x)==true){
            //erro ao inserir
        }else{
            tabela[hash(x)].inserir(x);
        }
    }

    boolean pesquisar(int x){
        return tabela[hash(x)].pesquisar(x);
    }

    void remover(int x){
        return tabela[hash(x)].remover(x);
    }
}