#include <stdio.h> 
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

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

void remove_quotes_and_trim(char *str) {
    if (str[0] == '"') {
        memmove(str, str + 1, strlen(str));
    }
    int len = strlen(str);
    if (len > 0 && str[len - 1] == '"') {
        str[len - 1] = '\0';
        len--;
    }
    int i = 0;
    while (isspace(str[i]) && i < len) i++;
    if (i > 0) {
        memmove(str, str + i, len - i + 1);
        len -= i;
    }
    while (len > 0 && isspace(str[len - 1])) {
        str[--len] = '\0';
    }
    if (len == 0) {
        strcpy(str, "NaN");
    }
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

int comparar(const Show *a, const Show *b) {
    int cmp = strcmp(a->_type, b->_type);
    if (cmp != 0) return cmp;
    return strcmp(a->_title, b->_title);
}

int main() {
    char entrada[20];
    Show shows[1000];
    int n = 0;

    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0)
            break;

        Show novo;
        if (buscar_por_show_id("/tmp/disneyplus.csv", entrada, &novo)) {
            int i = n;
            while (i > 0 && comparar(&novo, &shows[i - 1]) < 0) {
                shows[i] = shows[i - 1];
                i--;
            }
            shows[i] = novo;
            n++;
        }
    }

    for (int i = 0; i < n && i < 10; i++) {
        imprimir_show(&shows[i]);
    }

    return 0;
}
