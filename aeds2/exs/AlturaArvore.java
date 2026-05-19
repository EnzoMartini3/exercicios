class Arvore{
    public int alturaArvore(No raiz){
        int maxEsq = alturaArvore(raiz.esq);
        int maxDir = alturaArvore(raiz.dir);
        if(maxDir>maxEsq){
            return maxDir+1;
        }else{
            return maxEsq+1;
        }

    }
}