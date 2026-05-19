

public static int[] vetorOrdenado(int[] vetA, int[] vetB){

    int i=vetA.length();
    int j=vetB.length();

    while((i > 0) && (j > 0)){
        if(vetA[i] > vetB[j]){
            inserirA();
            inserirB();
        }else{
            inserirB();
            inserirA();
        }
    }
}