#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main() {
    FILE *arquivo;
    char nomeArquivo[] = "arquivo.txt"; // Arquivo binário para armazenar os números
    int n;
    double valor;

    // Lendo o número de valores
    scanf("%d", &n);

    // Abrindo o arquivo para escrita binária
    arquivo = fopen(nomeArquivo, "wb");
    if (arquivo == NULL) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    // Escrevendo os números no arquivo
    for (int i = 0; i < n; i++) {
        scanf("%lf", &valor);
        fwrite(&valor, sizeof(double), 1, arquivo);
    }
    fclose(arquivo); // Fecha o arquivo após escrita

    // Reabrindo o arquivo para leitura binária
    arquivo = fopen(nomeArquivo, "rb");

    // Lendo os números de trás para frente
    for (int i = 0; i < n; i++) {
        fseek(arquivo, -((i + 1) * sizeof(double)), SEEK_END); // Move o ponteiro para trás
        fread(&valor, sizeof(double), 1, arquivo); // Lê o número

        // Verifica se o número é um inteiro (parte decimal == 0)
        if (valor == floor(valor)) {
            printf("%d\n", (int)valor); // Imprime sem casas decimais
        } else {
            printf("%g\n", valor); // Mantém casas decimais se necessário
        }
    }

    fclose(arquivo); // Fecha o arquivo após leitura
    return 0;
}
