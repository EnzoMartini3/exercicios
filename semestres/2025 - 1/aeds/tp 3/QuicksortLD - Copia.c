#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_STR 300
#define MAX_LIST 50

typedef struct {
    int day, month, year;
} Date;

typedef struct {
    char showId[MAX_STR];
    char type[MAX_STR];
    char title[MAX_STR];
    char director[MAX_STR];
    char cast[MAX_LIST][MAX_STR];
    int castCount;
    char country[MAX_STR];
    char dateAdded[MAX_STR];
    int releaseYear;
    char rating[MAX_STR];
    char duration[MAX_STR];
    char listedIn[MAX_LIST][MAX_STR];
    int listedCount;
} Show;

typedef struct Node {
    Show data;
    struct Node* prev;
    struct Node* next;
} Node;

typedef struct {
    Node* head;
    Node* tail;
} DoublyLinkedList;

long comparacoes = 0;

void initList(DoublyLinkedList* list) {
    list->head = list->tail = NULL;
}

void append(DoublyLinkedList* list, Show s) {
    Node* node = (Node*)malloc(sizeof(Node));
    node->data = s;
    node->next = NULL;
    node->prev = list->tail;
    if (list->tail)
        list->tail->next = node;
    else
        list->head = node;
    list->tail = node;
}

int size(DoublyLinkedList* list) {
    int count = 0;
    Node* cur = list->head;
    while (cur) {
        count++;
        cur = cur->next;
    }
    return count;
}

Node* getAt(DoublyLinkedList* list, int index) {
    Node* cur = list->head;
    for (int i = 0; i < index && cur; i++) cur = cur->next;
    return cur;
}

int getMonthNumber(char* month) {
    char* months[] = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    for (int i = 0; i < 12; i++) {
        if (strcasecmp(month, months[i]) == 0) return i + 1;
    }
    return 0;
}

Date parseDate(char* dateStr) {
    Date d = {0, 0, 0};
    if (strcmp(dateStr, "NaN") == 0) return d;

    char month[20];
    int day, year;
    if (sscanf(dateStr, "%s %d, %d", month, &day, &year) == 3) {
        d.day = day;
        d.month = getMonthNumber(month);
        d.year = year;
    }
    return d;
}

int compareShows(Show* a, Show* b) {
    comparacoes++;
    Date da = parseDate(a->dateAdded);
    Date db = parseDate(b->dateAdded);

    if (da.year != db.year) return da.year - db.year;
    if (da.month != db.month) return da.month - db.month;
    if (da.day != db.day) return da.day - db.day;
    return strcmp(a->title, b->title);
}

void swap(DoublyLinkedList* list, int i, int j) {
    Node* ni = getAt(list, i);
    Node* nj = getAt(list, j);
    if (ni && nj) {
        Show temp = ni->data;
        ni->data = nj->data;
        nj->data = temp;
    }
}

int partition(DoublyLinkedList* list, int low, int high) {
    Show* pivot = &getAt(list, high)->data;
    int i = low - 1;
    for (int j = low; j < high; j++) {
        if (compareShows(&getAt(list, j)->data, pivot) <= 0) {
            i++;
            swap(list, i, j);
        }
    }
    swap(list, i + 1, high);
    return i + 1;
}

void quicksort(DoublyLinkedList* list, int low, int high) {
    if (low < high) {
        int pi = partition(list, low, high);
        quicksort(list, low, pi - 1);
        quicksort(list, pi + 1, high);
    }
}

void removeQuotesAndTrim(char* str) {
    char* start = str;
    char* end;

    if (*start == '"') start++;
    end = str + strlen(str) - 1;
    if (*end == '"') *end = '\0';

    while (isspace(*start)) start++;
    while (end > start && isspace(*end)) *end-- = '\0';

    if (start != str) memmove(str, start, strlen(start) + 1);
}

void splitAndSort(char* input, char output[MAX_LIST][MAX_STR], int* count) {
    *count = 0;
    if (strcmp(input, "NaN") == 0) return;

    char* token = strtok(input, ",");
    while (token && *count < MAX_LIST) {
        removeQuotesAndTrim(token);
        strcpy(output[*count], token);
        (*count)++;
        token = strtok(NULL, ",");
    }

    for (int i = 0; i < *count - 1; i++) {
        for (int j = i + 1; j < *count; j++) {
            if (strcmp(output[i], output[j]) > 0) {
                char tmp[MAX_STR];
                strcpy(tmp, output[i]);
                strcpy(output[i], output[j]);
                strcpy(output[j], tmp);
            }
        }
    }
}

Show parseShow(char* line) {
    Show s;
    strcpy(s.showId, "NaN");
    strcpy(s.type, "NaN");
    strcpy(s.title, "NaN");
    strcpy(s.director, "NaN");
    strcpy(s.country, "NaN");
    strcpy(s.dateAdded, "NaN");
    s.releaseYear = 0;
    strcpy(s.rating, "NaN");
    strcpy(s.duration, "NaN");
    s.castCount = 0;
    s.listedCount = 0;

    char* fields[12];
    int f = 0;
    int inQuotes = 0;
    char* token = strtok(line, ",");
    while (token && f < 12) {
        fields[f++] = token;
        token = strtok(NULL, ",");
    }

    if (f > 0) strcpy(s.showId, fields[0]);
    if (f > 1) strcpy(s.type, fields[1]);
    if (f > 2) strcpy(s.title, fields[2]);
    if (f > 3) strcpy(s.director, fields[3]);
    if (f > 4) {
        splitAndSort(fields[4], s.cast, &s.castCount);
    }
    if (f > 5) strcpy(s.country, fields[5]);
    if (f > 6) strcpy(s.dateAdded, fields[6]);
    if (f > 7) s.releaseYear = atoi(fields[7]);
    if (f > 8) strcpy(s.rating, fields[8]);
    if (f > 9) strcpy(s.duration, fields[9]);
    if (f > 10) {
        splitAndSort(fields[10], s.listedIn, &s.listedCount);
    }

    return s;
}

int main() {
    DoublyLinkedList list;
    initList(&list);

    char entrada[MAX_STR];
    while (1) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\n")] = 0;
        if (strcmp(entrada, "FIM") == 0) break;

        FILE* fp = fopen("/tmp/disneyplus.csv", "r");
        if (!fp) {
            perror("Erro ao abrir arquivo");
            return 1;
        }

        char linha[1024];
        fgets(linha, sizeof(linha), fp); // pular header

        while (fgets(linha, sizeof(linha), fp)) {
            if (strncmp(linha, entrada, strlen(entrada)) == 0 && linha[strlen(entrada)] == ',') {
                Show s = parseShow(linha);
                append(&list, s);
                break;
            }
        }
        fclose(fp);
    }

    clock_t start = clock();
    quicksort(&list, 0, size(&list) - 1);
    clock_t end = clock();

    FILE* out = fopen("matricula_quicksort3.txt", "w");
    fprintf(out, "Matricula: 850602\tTempo: %.6f\tComparacoes: %ld\n",
            (double)(end - start) / CLOCKS_PER_SEC, comparacoes);
    fclose(out);

    for (Node* cur = list.head; cur != NULL; cur = cur->next) {
        Show s = cur->data;
        printf("=> %s ## %s ## %s ## %s ## [", s.showId, s.title, s.type, s.director);
        for (int j = 0; j < s.castCount; j++) {
            printf("%s", s.cast[j]);
            if (j < s.castCount - 1) printf(", ");
        }
        printf("] ## %s ## %s ## %d ## %s ## %s ## [", s.country, s.dateAdded, s.releaseYear, s.rating, s.duration);
        for (int j = 0; j < s.listedCount; j++) {
            printf("%s", s.listedIn[j]);
            if (j < s.listedCount - 1) printf(", ");
        }
        printf("] ##\n");
    }

    return 0;
}
