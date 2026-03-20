#include <stdio.h>
#include <string.h>

#define MAX 1000

char oeste[MAX][10], norte[MAX][10], sul[MAX][10], leste[MAX][10];
int iOeste = 0, iNorte = 0, iSul = 0, iLeste = 0;

int main() {
    int direcao;
    char aviao[10];

    while (1) {
        scanf("%d", &direcao);
        if (direcao == 0)
            break;

        scanf("%s", aviao);

        switch (direcao) {
            case -1: strcpy(oeste[iOeste], aviao);
            iOeste++; 
            break;
            
            case -3: strcpy(norte[iNorte], aviao);
            iNorte++;
            break;
            
            case -2: strcpy(sul[iSul], aviao); 
            iSul++;
            break;

            case -4: strcpy(leste[iLeste], aviao); 
            iLeste++;
            break;
        }
    }

    // Imprime os aviões do oeste
    for (int i = 0; i < iOeste; i++) {
        printf("%s ", oeste[i]);
    }

    // Intercala norte e sul
    int i = 0;
    while (i < iNorte || i < iSul) {
        if (i < iNorte) printf("%s ", norte[i]);
        if (i < iSul)   printf("%s ", sul[i]);
        i++;
    }

    // Imprime os aviões do leste
    for (int i = 0; i < iLeste; i++) {
        printf("%s ", leste[i]);
    }

    printf("\n");
    return 0;
}
