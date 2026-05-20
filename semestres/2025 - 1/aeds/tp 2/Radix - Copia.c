#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include <math.h>

/*
EXPLCIACAO RADIX

O Radixsort é um algoritmo de ordenação não comparativo que utiliza a ordenação por dígitos, baseando-se em cada dígito do número a ser ordenado. Ele funciona de maneira eficiente quando os números envolvidos têm um número fixo de dígitos. O algoritmo processa os números começando pelo dígito menos significativo (LRU - Least Significant Digit) ou mais significativo (MSD - Most Significant Digit), utilizando uma técnica chamada de contagem (counting sort) para ordenar os dígitos.

A principal vantagem do Radixsort é que ele pode ser mais rápido do que algoritmos de comparação, como QuickSort ou MergeSort, para conjuntos de dados grandes com números ou strings com tamanho fixo. Porém, sua eficiência depende de uma estrutura de dados de chave fixa, como números inteiros ou strings com tamanho limitado.

Melhor caso, pior caso e caso médio: 
O(d.(n+b)), onde: n é o número de elementos, d é o número de dígitos necessários para representar o maior número ou a maior chave. b é a base usada para a ordenação.

*/

typedef struct {
    char _show_id[100];
    char _type[100];
    char _title[100];
    char _director[100];
    char _cast[20][100];
    int _cast_count;
    char _country[100];
    char _date_added[100];
    int _release_year;
    char _rating[100];
    char _duration[100];
    char _listed_in[20][100];
    int _listed_count;
} Show;

// Variável global para contar comparações
long comparacoes = 0;

void swap(Show *a, Show *b) {
    Show temp = *a;
    *a = *b;
    *b = temp;
}

void parse_csv_line(const char *line, char tokens[20][1000]) {
    int token_count = 0;
    int i = 0;
    int j = 0;
    int in_quotes = 0;

    while (line[i] != '\0') {
        if (line[i] == '"') {
            in_quotes = !in_quotes;  // Alterna entre dentro e fora de aspas
        } else if (line[i] == ',' && !in_quotes) {
            tokens[token_count][j] = '\0';
            token_count++;
            j = 0;
        } else {
            tokens[token_count][j++] = line[i];
        }
        i++;
    }
    tokens[token_count][j] = '\0';  // Adiciona o último token
}

// Função auxiliar para obter o dígito de uma posição específica
int get_digit(int num, int digit_pos) {
    return (num / (int)pow(10, digit_pos)) % 10;
}

// Função de contagem (counting sort) para ordenar baseado em um dígito
void counting_sort(Show arr[], int n, int digit_pos) {
    Show output[n];
    int count[10] = {0};

    // Contagem de ocorrências
    for (int i = 0; i < n; i++) {
        int digit = get_digit(arr[i]._release_year, digit_pos);
        count[digit]++;
    }

    // Acumulando os valores de contagem
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }

    // Colocando os elementos na posição correta
    for (int i = n - 1; i >= 0; i--) {
        int digit = get_digit(arr[i]._release_year, digit_pos);
        output[count[digit] - 1] = arr[i];
        count[digit]--;
    }

    // Copiando a lista ordenada de volta para arr
    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

// Função principal do Radixsort
void radix_sort(Show arr[], int n) {
    int max_release_year = arr[0]._release_year;
    for (int i = 1; i < n; i++) {
        if (arr[i]._release_year > max_release_year) {
            max_release_year = arr[i]._release_year;
        }
    }

    // Aplicando Counting Sort para cada dígito
    for (int digit_pos = 0; max_release_year / (int)pow(10, digit_pos) > 0; digit_pos++) {
        counting_sort(arr, n, digit_pos);
    }

    // Ordena por title em caso de empate no release_year
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (arr[i]._release_year == arr[j]._release_year) {
                if (strcmp(arr[i]._title, arr[j]._title) > 0) {
                    swap(&arr[i], &arr[j]);
                }
            }
        }
    }
}

// Funções auxiliares (sem alterações)

void remove_quotes_and_trim(char *str) {
    if (str[0] == '"') memmove(str, str + 1, strlen(str));
    int len = strlen(str);
    if (len > 0 && str[len - 1] == '"') str[--len] = '\0';

    int i = 0;
    while (isspace(str[i]) && i < len) i++;
    if (i > 0) {
        memmove(str, str + i, len - i + 1);
        len -= i;
    }
    while (len > 0 && isspace(str[len - 1])) str[--len] = '\0';

    if (len == 0) strcpy(str, "NaN");
}

void split_and_sort(const char *src, char dest[][100], int *count) {
    char temp[1000];
    strcpy(temp, src);
    *count = 0;
    remove_quotes_and_trim(temp);
    if (strcmp(temp, "NaN") == 0) return;

    char *token = strtok(temp, ",");
    while (token && *count < 20) {
        remove_quotes_and_trim(token);
        strcpy(dest[(*count)++], token);
        token = strtok(NULL, ",");
    }

    for (int i = 0; i < *count - 1; i++) {
        for (int j = i + 1; j < *count; j++) {
            if (strcmp(dest[i], dest[j]) > 0) {
                char temp_swap[100];
                strcpy(temp_swap, dest[i]);
                strcpy(dest[i], dest[j]);
                strcpy(dest[j], temp_swap);
            }
        }
    }
}

Show new_show() {
    Show s;
    strcpy(s._show_id, "NaN");
    strcpy(s._type, "NaN");
    strcpy(s._title, "NaN");
    strcpy(s._director, "NaN");
    s._cast_count = 0;
    strcpy(s._country, "NaN");
    strcpy(s._date_added, "NaN");
    s._release_year = 0;
    strcpy(s._rating, "NaN");
    strcpy(s._duration, "NaN");
    s._listed_count = 0;
    return s;
}

void ler_show(Show *s, const char *linha) {
    char tokens[20][1000];
    parse_csv_line(linha, tokens);
    *s = new_show();

    if (strlen(tokens[0]) > 0) remove_quotes_and_trim(tokens[0]);
    strcpy(s->_show_id, strlen(tokens[0]) > 0 ? tokens[0] : "NaN");

    if (strlen(tokens[1]) > 0) remove_quotes_and_trim(tokens[1]);
    strcpy(s->_type, strlen(tokens[1]) > 0 ? tokens[1] : "NaN");

    if (strlen(tokens[2]) > 0) remove_quotes_and_trim(tokens[2]);
    strcpy(s->_title, strlen(tokens[2]) > 0 ? tokens[2] : "NaN");

    if (strlen(tokens[3]) > 0) remove_quotes_and_trim(tokens[3]);
    strcpy(s->_director, strlen(tokens[3]) > 0 ? tokens[3] : "NaN");

    if (strlen(tokens[4]) > 0) {
        remove_quotes_and_trim(tokens[4]);
        split_and_sort(tokens[4], s->_cast, &s->_cast_count);
    }

    if (strlen(tokens[5]) > 0) remove_quotes_and_trim(tokens[5]);
    strcpy(s->_country, strlen(tokens[5]) > 0 ? tokens[5] : "NaN");

    if (strlen(tokens[6]) > 0) remove_quotes_and_trim(tokens[6]);
    strcpy(s->_date_added, strlen(tokens[6]) > 0 ? tokens[6] : "NaN");

    if (strlen(tokens[7]) > 0) {
        remove_quotes_and_trim(tokens[7]);
        s->_release_year = atoi(tokens[7]);
    }

    if (strlen(tokens[8]) > 0) remove_quotes_and_trim(tokens[8]);
    strcpy(s->_rating, strlen(tokens[8]) > 0 ? tokens[8] : "NaN");

    if (strlen(tokens[9]) > 0) remove_quotes_and_trim(tokens[9]);
    strcpy(s->_duration, strlen(tokens[9]) > 0 ? tokens[9] : "NaN");

    if (strlen(tokens[10]) > 0) {
        remove_quotes_and_trim(tokens[10]);
        split_and_sort(tokens[10], s->_listed_in, &s->_listed_count);
    }
}

int main() {
    char entrada[20];
    Show vetor[1000];
    int n = 0;

    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0)
            break;

        FILE *fp = fopen("/tmp/disneyplus.csv", "r");
        if (!fp) return 1;
        char linha[1000];
        fgets(linha, 1000, fp);

        while (fgets(linha, 1000, fp)) {
            char tokens[20][1000];
            parse_csv_line(linha, tokens);
            if (strcmp(tokens[0], entrada) == 0) {
                ler_show(&vetor[n++], linha);
                break;
            }
        }
        fclose(fp);
    }

    clock_t inicio = clock();
    radix_sort(vetor, n);
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    FILE *log = fopen("matricula_radixsort.txt", "w");
    fprintf(log, "Matricula: 801315\tTempo: %.6lf\tComparacoes: %ld\n", tempo, comparacoes);
    fclose(log);

    for (int i = 0; i < n; i++) {
        printf("=> %s ## %s ## %s ## %s ## [", vetor[i]._show_id, vetor[i]._title, vetor[i]._type, vetor[i]._director);
        for (int j = 0; j < vetor[i]._cast_count; j++) {
            printf("%s", vetor[i]._cast[j]);
            if (j < vetor[i]._cast_count - 1) printf(", ");
        }
        printf("] ## %s ## %s ## %d ## %s ## %s ## [", vetor[i]._country, vetor[i]._date_added, vetor[i]._release_year, vetor[i]._rating, vetor[i]._duration);
        for (int j = 0; j < vetor[i]._listed_count; j++) {
            printf("%s", vetor[i]._listed_in[j]);
            if (j < vetor[i]._listed_count - 1) printf(", ");
        }
        printf("] ##\n");
    }

    return 0;
}
