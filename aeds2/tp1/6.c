#include <stdio.h>

int tamanhoString(char s[]){
    int tam=0;
    while(s[tam] != '\0'){
        tam++;
    }
    return tam;
}

int anagrama(char s1[], char s2[]){
    int tam = tamanhoString(s1);
    int tam2 = tamanhoString(s2);

    if(tam != tam2) return 0; //tamanho diferente = n é anagrama
    
    for(int i=0; i<tam; i++){
        if(s1[i] >= 'A' && s1[i] <= 'Z'){
            s1[i] = s1[i] + 32;               //TOLOWER PARA S1
        }
    }
    for(int j=0; j<tam2; j++){
        if(s2[j] >= 'A' && s2[j] <= 'Z'){
            s2[j] = s2[j] + 32;               //TOLOWER PARA S2
        }
    }

    int encontrado = 0;
    for(int i=0; i<tam; i++){ //pesquisar no primeiro vetor
        encontrado = 0;
        for(int j=0; j<tam2; j++){ //pesquisar no segundo vetor
            if(s1[i] == s2[j]){
                encontrado = 1;
                s2[j] = '*'; //impede que a letra repita
                break; //encontramos, tudo certo
            }
        } //com o asterisco nao temos problemas de AAA vs ABC
        if(encontrado == 0){
            return 0;
        }
        
    }
    return 1;
}

int main(){
    char s1[200];
    char s2[200];
    while (scanf("%s", s1) != EOF) {
        if (s1[0] == 'F' && s1[1] == 'I' && s1[2] == 'M' && s1[3] == '\0') {
            break;       //precisamos ler a primeira e segunda palavra separadas
        }
        scanf("%s", s2); //le a segunda palavra do par
        if (anagrama(s1, s2)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }
    return 0;
}