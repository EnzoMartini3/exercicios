public class Celula {
    int elemento;
    Celula prox;

    Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class ListaFlexivel {
    private Celula primeiro;

    public ListaFlexivel() {
        primeiro = null;
    }

public int removeSegunda(){
    Celula e=primeiro;
        for (int i = 0; i < 2 && e != null; i++) {
        e.prox = e.prox.prox;
        e.prox = null; 
    }
}

public int somaElementos(){
    Celula s;
    int soma=0;
    for(s=primeiro; s!=null; s.prox){
        soma += s;
    }
    return soma;
}

public int maiorElemento(){
    Celula s;
    int maior=0;
    for(s=primeiro; s!=null; s.prox){
        if(s>maior){
            maior=s;
        }
    }
    return maior;
}

public int terceiro(){
    Celula e;
    for(int i=0,e=primeiro; i<3,e!=null; e.prox,i++){
        return e;
    }
}

public int paresMultiplos5(){
    Celula s;
    int maior=0;
    for(s=primeiro; s!=null; s.prox){
        if(s%10==0){
            maior++;
        }
    }
    return maior;
}

/*
5 - Em nossa lista flexível, implemente um método que inverte a ordem dos seus elementos.
7 - Modifique nossa lista flexível, de tal forma que ela não tenha a referência último.
8 - Modifique nossa lista flexível, criando uma fila flexível.
9 - Modifique nossa lista flexível, criando uma pilha flexível.
10 - Modifique nossa lista flexível, criando uma lista ordenada.
11 - Modifique nossa lista flexível, criando uma lista duplamente encadeada.


1 - Em nossa lista flexível, implemente um método que remove a segunda posição válida.
2 - Em nossa lista flexível, implemente um método que retorna a soma os elementos contidos na mesma.
3 - Em nossa lista flexível, implemente um método que retorna o maior elemento contido na mesma.
4 - Em nossa lista flexível, implemente um método que retorna o terceiro elemento supondo que o mesmo existe.
5 - Em nossa lista flexível, implemente um método que inverte a ordem dos seus elementos.
6 - Em nossa lista flexível, implemente um método que retorna o número de elementos pares and múltiplos de cinco contidos na mesma.
7 - Modifique nossa lista flexível, de tal forma que ela não tenha a referência último.
8 - Modifique nossa lista flexível, criando uma fila flexível.
9 - Modifique nossa lista flexível, criando uma pilha flexível.
10 - Modifique nossa lista flexível, criando uma lista ordenada.
11 - Modifique nossa lista flexível, criando uma lista duplamente encadeada.
 */
 
{
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}
}