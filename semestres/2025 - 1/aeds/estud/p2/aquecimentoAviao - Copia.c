/*O aeroporto de Congonhas recebe todos os dias uma média de 600 pousos e decolagens, ou cerca de 36 por hora. No último ano, foram exatamente 223.989 movimentos aéreos. Para organizar todo o fluxo de aviões que chegam a Congonhas e saem de lá, a torre de controle funciona o tempo inteiro com nível máximo de atenção. Para descartar qualquer possibilidade de erro humano o chefe do controle de tráfego aéreo de Congonhas contratou você para desenvolver um programa que organize automaticamente o fluxo de aviões no campo de pouso.

Para isso, basta seguir o seguinte protocolo, os aviões que veem do lado Oeste da pista têm maior prioridade de serem colocados na fila, pois são aqueles que estão mais próximo do localizador (início da pista). Já os aviões que estão se aproximando pelo lado Norte e Sul, devem ser inseridos na fila 1 por vez, ou seja, insere-se 1 avião do lado Norte e em seguida 1 avião do lado Sul. Por último, insere-se o próximo avião que esteja se aproximando ao lado leste da pista.

Entrada
A entrada é composta por um número inteiro P, representando o ponto cardeal do avião que entrou no campo da pista (-4 <= P <= -1), onde (-4 representa o lado leste, -3 o lado norte, -2 lado sul e -1 lado oeste) . Em seguida é realizada a entrada dos respectivos aviões, compostos de um identificador começando com a letra “A” seguida de um número inteiro I (1 <= I <= 1000). A qualquer momento é permitido trocar o ponto cardeal, e inserir novas aeronaves, repetidamente até que o controlador finalize a sessão com o dígito 0.

Saída
A saída é composta de uma linha contendo as aeronaves enfileiradas pela ordem do protocolo estabelecido pelo aeroporto.*/

#include <stdio.h>
#include <string.h>
#include <boolean.h>

int e;
int impress=4;
int tleste=0,l=0;
int toeste=0,o=0;
int tsul=0,s=0;
int tnorte=0,n=0;

int[50] leste;
int[50] oeste;
int[50] norte;
int[50] sul;

void funcao(int e){
    char[50] aviao;
    scanf(""); //pega o numero do aviao
    if(!(aviao[0]=='A')){ //entrada nao comecar com A = novo numero
        funcao(aviao[1]);
    }
        if(e==-4){
            leste[tleste] = aviao;
            tleste++;
        }else if(e==-3){
            norte[tnorte] = aviao;
            tnorte++;
        }else if(e==-2){
            sul[tsul] = aviao;
            tsul++;
        }else if(e==-1){
            oeste[toeste] = aviao;
            toeste++;
        }
}


void impressao(){
    //alterna entre os 4 vetores de direcao, verificando se a variavel "t" correspondente é 0. se sim, imprime e reduz o "t".

    if(toeste==0){ //oeste
        impress--;
    }else{
        printf("%s",oeste[o]);
        o++;
        toeste--;
    }

    if(tnorte!=0){ //norte
        impress--;
    }else{
        printf("%s",norte[n]);
        n++;
        toeste--;
    }

    if(tsul!=0){ //sul
        impress--;
        
    else{
        printf("%s",sul[s]);
        s++;
        toeste--;
    }

    }if(tleste!=0){ //leste
        impress--;
    }else{
        printf("%s",leste[l]);
        l++;
        toeste--;
    }

    if(impress!=0){ //parada
        impressao();
    }
}

int main(){
    do{
        scanf("%i", &e);
        funcao(e);
    }while (e!=0);
    impressao();
    return 0;
}


