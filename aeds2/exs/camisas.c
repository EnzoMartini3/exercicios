//implementar
#include <stdio.h>

void func(char nomes[], char specs[]){
    
}

int main(){
    int n;
    char nomes[200];
    char specs[200];
    scanf("%i", &n);
    while(n>0){
        for(int i=0; i<n; i++){
           scanf(" %[^\n]\n %[^\n]");
        }
        func(nomes, specs);
        scanf("%i", &n);
    }
    
    return 0;
}