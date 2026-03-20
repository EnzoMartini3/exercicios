// temos 2 arvores trie. identifique as palavras em comum e insira em uma terceira

void percorrer(No no1, No no2, String palavra, Trie resp){
    if(no1||no2==null){
        return;
    }
    if(no1.isFolha & no2.isFolha){
        resp.inserir(palavra);
    }
    for(i=0;i < no1.tamanho; i++){
        if(no1.prox[i]!=null && no2.prox[i]!=null){
            charAtual = (char)i;
            percorrer(no1.prox[i], no2.prox[i], palavra+charAtual, resp);
        }
    }
}

Trie intersecao(Trie a1, Trie a2){
    Trie resp = new Trie();
    percorrer(a1.raiz,a2.raiz,"",resp);
    return resp;
}

