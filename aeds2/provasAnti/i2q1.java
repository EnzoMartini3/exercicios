class ArvoreArvore{
    No raiz;

    No achaLetra(char l){
        No ret = raiz;
        while(ret.letra != l){
            if(ret.letra > l){
                ret = ret.dir;
            }else{
                ret = ret.esq;
            }
        }
        return ret;   
    }

    int caminha2(No2 aux, int ret, char prim, char ult){
        int g = aux.palavra.length() - 1;
        if(ult == aux.palavra.charAt(g) && prim == aux.palavra.charAt(0)){
            ret++;
        }
        
        return ret;
    }

    public static int contarPalavras(char prim, char ult){
        int ps = 0;
        No noPrim = achaLetra(prim);
        int ret = caminha2(No.raiz, 0, prim, ult);
        return ret;
    }
}

class No{
    char letra;
    No esq, dir;
    No2 raiz;
}

class No2{
    String palavra;
    No2 esq, dir;
}