#include <stdio.h>

void func(char vet[]){
    int tam=0;
    for(int i=0; vet[i]!='\0'; i++){
        if(vet[i] == '@'){
            vet[i] = 'a';
        }else if(vet[i] == '&'){
            vet[i] = 'e';
        }else if(vet[i] == '!'){
            vet[i] = 'i';
        }else if(vet[i] == '*'){
            vet[i] = 'o';
        }else if(vet[i] == '#'){
            vet[i] = 'u';
        }
        tam++;
    }
    for(int j=0; j<tam; j++){
        printf("%c", vet[j]);
    }
}

int main(){
    char entrada[500];
    while((scanf(" %[^\n]", entrada)) == 1){
        func(entrada);
    }

    return 0;
}