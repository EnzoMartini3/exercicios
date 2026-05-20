#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

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

long comparacoes = 0;

#include <time.h>

void gerar_log(double tempo_execucao, long comparacoes) {
    FILE *log = fopen("matricula_shellsort.txt", "w");
    if (log) {
        fprintf(log, "850602\t%lf\t%ld\n", tempo_execucao, comparacoes);
        fclose(log);
    }
}

void shellsort(Show arr[], int n) {
    for (int gap = n / 2; gap > 0; gap /= 2) {
        for (int i = gap; i < n; i++) {
            Show temp;
            clone_show(&temp, &arr[i]);
            int j;
            for (j = i; j >= gap; j -= gap) {
                comparacoes++;
                int cmp_type = strcmp(arr[j - gap]._type, temp._type);
                int cmp_title = strcmp(arr[j - gap]._title, temp._title);
                if (cmp_type > 0 || (cmp_type == 0 && cmp_title > 0)) {
                    clone_show(&arr[j], &arr[j - gap]);
                } else {
                    break;
                }
            }
            clone_show(&arr[j], &temp);
        }
    }
}

void remove_quotes(char *str) {
    if (str[0] == '"') memmove(str, str + 1, strlen(str));
    int len = strlen(str);
    if (str[len - 1] == '"') str[len - 1] = '\0';
}

void split_and_sort(const char *src, char dest[][100], int *count) {
    char temp[1000];
    strcpy(temp, src);
    *count = 0;
    char *token = strtok(temp, ",");
    
    while (token && *count < 20) {
        while (*token == ' ') token++;
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

// Construtor vazio
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

// Construtor com parâmetros básicos
Show new_show_param(const char *id, const char *type, const char *title) {
    Show s = new_show();
    strcpy(s._show_id, id);
    strcpy(s._type, type);
    strcpy(s._title, title);
    return s;
}

// Métodos get/set
const char* get_title(Show *s) { return s->_title; }
void set_title(Show *s, const char *title) { strcpy(s->_title, title); }

void clone_show(Show *dest, const Show *orig) {
    *dest = *orig;
}

// Lê campos de uma linha do CSV
int parse_csv_line(const char *linha, char tokens[20][1000]) {
    int i = 0, j = 0, col = 0;
    int dentro_aspas = 0;
    char token[1000] = "";

    while (linha[i] != '\0') {
        if (linha[i] == '"') {
            dentro_aspas = !dentro_aspas;
        } else if (linha[i] == ',' && !dentro_aspas) {
            token[j] = '\0';
            strcpy(tokens[col++], token);
            j = 0;
        } else {
            token[j++] = linha[i];
        }
        i++;
    }
    token[j] = '\0';
    strcpy(tokens[col++], token);
    return col;
}

// Lê os atributos a partir da linha do CSV
void ler_show(Show *s, const char *linha) {
    char tokens[20][1000];
    parse_csv_line(linha, tokens);

    strcpy(s->_show_id, tokens[0]);
    strcpy(s->_type, tokens[1]);
    strcpy(s->_title, tokens[2]);
    strcpy(s->_director, strlen(tokens[3]) ? tokens[3] : "NaN");

    s->_cast_count = 0;
    if (strlen(tokens[4])) {
        split_and_sort(tokens[4], s->_cast, &s->_cast_count);
    }

    strcpy(s->_country, strlen(tokens[5]) ? tokens[5] : "NaN");
    strcpy(s->_date_added, strlen(tokens[6]) ? tokens[6] : "NaN");
    s->_release_year = strlen(tokens[7]) ? atoi(tokens[7]) : 0;
    strcpy(s->_rating, strlen(tokens[8]) ? tokens[8] : "NaN");
    strcpy(s->_duration, strlen(tokens[9]) ? tokens[9] : "NaN");

    s->_listed_count = 0;
    if (strlen(tokens[10])) {
        split_and_sort(tokens[10], s->_listed_in, &s->_listed_count);
    }
}

// Imprime no formato pedido
void imprimir_show(const Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->_show_id, s->_title, s->_type, s->_director);
    for (int i = 0; i < s->_cast_count; i++) {
        printf("%s", s->_cast[i]);
        if (i < s->_cast_count - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->_country, s->_date_added, s->_release_year, s->_rating, s->_duration);
    for (int i = 0; i < s->_listed_count; i++) {
        printf("%s", s->_listed_in[i]);
        if (i < s->_listed_count - 1) printf(", ");
    }
    printf("] ##\n");
}

// Procura show_id no arquivo CSV
int buscar_por_show_id(const char *filename, const char *id_procurado, Show *resultado) {
    FILE *fp = fopen(filename, "r");
    if (!fp) return 0;

    char linha[1000];
    fgets(linha, 1000, fp); // cabeçalho

    while (fgets(linha, 1000, fp)) {
        char tokens[20][1000];
        parse_csv_line(linha, tokens);
        if (strcmp(tokens[0], id_procurado) == 0) {
            ler_show(resultado, linha);
            fclose(fp);
            return 1;
        }
    }

    fclose(fp);
    return 0;
}

int main() {
    char entrada[20];
    Show lista[400];
    int tamanho = 0;

    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0)
            break;
        if (buscar_por_show_id("/tmp/disneyplus.csv", entrada, &lista[tamanho])) {
            tamanho++;
        }
    }

    clock_t inicio = clock();
    shellsort(lista, tamanho);
    clock_t fim = clock();

    double tempo_execucao = (double)(fim - inicio) / CLOCKS_PER_SEC;
    gerar_log(tempo_execucao, comparacoes);

    for (int i = 0; i < tamanho; i++) {
        imprimir_show(&lista[i]);
    }

    return 0;
}
