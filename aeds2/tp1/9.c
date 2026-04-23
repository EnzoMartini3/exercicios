#include <stdio.h>

// Metodo recursivo que recebe a string e o indice. A cada iteracao (chamada), pegamos o char da posicao,
void cesar(char s[], int i){
    if(s[i] == '\0'){
        printf("\n");
    } else {
        char c = s[i];
        printf("%c", (char)(c + 3));
        cesar(s, i + 1); // Chamada recursiva para a proxima posicao
    }
}

int isFim(char s[]){
    // Como o scanf %[^\n] ja limpa a entrada, s[3] sera sempre \0 no caso do "FIM"
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0');
}

int main(){
    char vet[1000];
    
    // O espaco antes de %[^\n] come o \n que sobra no buffer
    while(scanf(" %[^\n]", vet) == 1 && !isFim(vet)){
        cesar(vet, 0); // Chama a funcao recursiva comecando do indice 0
    }
    
    return 0;
}