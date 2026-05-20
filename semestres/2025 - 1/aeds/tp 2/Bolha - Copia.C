#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
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

typedef struct {
    int day, month, year;
} Date;

long comparacoes = 0;

int get_month_number(const char *month) {
    const char *months[] = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    for (int i = 0; i < 12; i++) {
        if (strcasecmp(month, months[i]) == 0)
            return i + 1;
    }
    return 0;
}

Date parse_date(const char *date_str) {
    Date d = {0, 0, 0};
    if (strcmp(date_str, "NaN") == 0) return d;

    char month[20];
    int day, year;
    if (sscanf(date_str, "%s %d, %d", month, &day, &year) == 3) {
        d.day = day;
        d.month = get_month_number(month);
        d.year = year;
    }
    return d;
}

int comparar_shows(const Show *a, const Show *b) {
    comparacoes++;
    Date da = parse_date(a->_date_added);
    Date db = parse_date(b->_date_added);

    if (da.year != db.year) return da.year - db.year;
    if (da.month != db.month) return da.month - db.month;
    if (da.day != db.day) return da.day - db.day;
    return strcmp(a->_title, b->_title);
}

void swap(Show *a, Show *b) {
    Show temp = *a;
    *a = *b;
    *b = temp;
}

int partition(Show arr[], int low, int high) {
    Show pivot = arr[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
        if (comparar_shows(&arr[j], &pivot) <= 0) {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return i + 1;
}

// ... (todas as structs e funções auxiliares permanecem as mesmas até o main)

void bubble_sort(Show arr[], int n) {
    int trocou = 1;
    for (int i = 0; i < n - 1 && trocou; i++) {
        trocou = 0;
        for (int j = 0; j < n - i - 1; j++) {
            if (comparar_shows(&arr[j], &arr[j + 1]) > 0) {
                swap(&arr[j], &arr[j + 1]);
                trocou = 1;
            }
        }
    }
}




// Funções auxiliares já existentes (sem mudanças)
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

int parse_csv_line(const char *linha, char tokens[20][1000]) {
    int i = 0, j = 0, col = 0, dentro_aspas = 0;
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
}int main() {
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
    bubble_sort(vetor, n);
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    FILE *log = fopen("matricula_bubblesort.txt", "w");
    fprintf(log, "Matricula: 850602\tTempo: %.6lf\tComparacoes: %ld\n", tempo, comparacoes);
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