class ArvoreArvore{
    No raiz;

    No acharLetra(char l){
        No ret = raiz;
        while(l != ret.letra){
            if(l > ret.letra){
                ret = ret.dir;
            }else{
                ret = ret.esq;
            }
        }
        return ret;
    }

    int caminha2(int pv, No2 aux, int mesmoChars){
        if(aux.palavra.length() == mesmoChars){
            pv++;
        }
        caminha2(pv, aux.esq, mesmoChars);
        caminha2(pv, aux.dir, mesmoChars);
        return pv;
    }

    int contarPalavras(String padrao){
        char l = padrao.charAt(0);
        No aux = acharLetra(l);
        int mesmoChars = padrao.length();
        int pv = caminha2(0, aux.raiz, mesmoChars);
        return pv;
    }
}

class No{
    char letra;
    No esq, dir;
    No2 raiz;
}

class No2{
    String palavra;
    No esq, dir;
}