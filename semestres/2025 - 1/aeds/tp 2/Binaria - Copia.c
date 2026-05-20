#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_SHOWS 10000
#define MAX_IDS 1000

typedef struct {
    char show_id[40];
    char type[40];
    char title[100];
    char director[100];
    char cast[40][100];
    int cast_count;
    char country[50];
    char date_added[50];
    int release_year;
    char rating[10];
    char duration[40];
    char listed_in[40][100];
    int listed_count;
} Show;

Show shows[MAX_SHOWS];
int total_shows = 0;

void remove_quotes(char *str) {
    int len = strlen(str);
    if (len >= 2 && str[0] == '"' && str[len - 1] == '"') {
        memmove(str, str + 1, len - 2);
        str[len - 2] = '\0';
    }
}

void split_and_sort(char *str, char array[][100], int *count) {
    char *token = strtok(str, ",");
    *count = 0;
    while (token != NULL && *count < 40) {
        while (*token == ' ') token++;
        strncpy(array[(*count)++], token, 100);
        token = strtok(NULL, ",");
    }

    for (int i = 0; i < *count - 1; i++) {
        for (int j = i + 1; j < *count; j++) {
            if (strcmp(array[i], array[j]) > 0) {
                char temp[100];
                strcpy(temp, array[i]);
                strcpy(array[i], array[j]);
                strcpy(array[j], temp);
            }
        }
    }
}

int parse_csv_line(char *line, char fields[][1000]) {
    int col = 0, i = 0, j = 0, in_quotes = 0;
    while (line[i] != '\0' && line[i] != '\n') {
        if (line[i] == '"') {
            in_quotes = !in_quotes;
        } else if (line[i] == ',' && !in_quotes) {
            fields[col][j] = '\0';
            col++;
            j = 0;
        } else {
            fields[col][j++] = line[i];
        }
        i++;
    }
    fields[col][j] = '\0';
    return col + 1;
}

void preencher_show(Show *s, char campos[][1000]) {
    strcpy(s->show_id, campos[0]);
    remove_quotes(s->show_id);

    strcpy(s->type, campos[1]);
    remove_quotes(s->type);

    strcpy(s->title, campos[2]);
    remove_quotes(s->title);

    strcpy(s->director, campos[3]);
    remove_quotes(s->director);

    char temp_cast[1000];
    strcpy(temp_cast, campos[4]);
    remove_quotes(temp_cast);
    split_and_sort(temp_cast, s->cast, &s->cast_count);

    strcpy(s->country, campos[5]);
    remove_quotes(s->country);

    strcpy(s->date_added, campos[6]);
    remove_quotes(s->date_added);

    s->release_year = atoi(campos[7]);

    strcpy(s->rating, campos[8]);
    remove_quotes(s->rating);

    strcpy(s->duration, campos[9]);
    remove_quotes(s->duration);

    char temp_listed[1000];
    strcpy(temp_listed, campos[10]);
    remove_quotes(temp_listed);
    split_and_sort(temp_listed, s->listed_in, &s->listed_count);
}

int compare_shows(const void *a, const void *b) {
    return strcmp(((Show *)a)->show_id, ((Show *)b)->show_id);
}

int binary_search_show_id(const char *id) {
    int left = 0, right = total_shows - 1;
    while (left <= right) {
        int mid = (left + right) / 2;
        int cmp = strcmp(shows[mid].show_id, id);
        if (cmp == 0) return mid;
        else if (cmp < 0) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}

void carregar_todos_shows(const char *filename) {
    FILE *fp = fopen(filename, "r");
    if (!fp) {
        perror("Erro ao abrir o arquivo");
        exit(1);
    }

    char linha[1000];
    fgets(linha, 1000, fp); // ignora cabeçalho

    while (fgets(linha, 1000, fp) && total_shows < MAX_SHOWS) {
        char campos[20][1000];
        int colunas = parse_csv_line(linha, campos);
        preencher_show(&shows[total_shows++], campos);
    }

    fclose(fp);
    qsort(shows, total_shows, sizeof(Show), compare_shows);
}

int main() {
    carregar_todos_shows("/tmp/disneyplus.csv");

    char entradas[MAX_IDS][40];
    int total_entradas = 0;

    char buffer[100];
    while (scanf("%s", buffer) != EOF) {
        if (strcmp(buffer, "FIM") == 0) break;
        strcpy(entradas[total_entradas++], buffer);
    }

    for (int i = 0; i < total_entradas; i++) {
        int idx = binary_search_show_id(entradas[i]);
        if (idx != -1) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    return 0;
}
