#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define MAX_SIZE 5
#define MAX_CAST 20
#define MAX_LISTED 20

typedef struct {
    char show_id[100];
    char type[100];
    char title[100];
    char director[100];
    char cast[MAX_CAST][100];
    int cast_count;
    char country[100];
    char date_added[100];
    int release_year;
    char rating[100];
    char duration[100];
    char listed_in[MAX_LISTED][100];
    int listed_count;
} Show;

typedef struct {
    Show data[MAX_SIZE];
    int front;
    int rear;
    int size;
} CircularQueue;

void initQueue(CircularQueue *q) {
    q->front = 0;
    q->rear = -1;
    q->size = 0;
}

int isFull(CircularQueue *q) {
    return q->size == MAX_SIZE;
}

int isEmpty(CircularQueue *q) {
    return q->size == 0;
}

void enqueue(CircularQueue *q, Show show) {
    q->rear = (q->rear + 1) % MAX_SIZE;
    q->data[q->rear] = show;
    q->size++;
}

Show dequeue(CircularQueue *q) {
    if (isEmpty(q)) {
        Show empty;
        memset(&empty, 0, sizeof(Show));
        return empty;
    }
    
    Show removed = q->data[q->front];
    q->front = (q->front + 1) % MAX_SIZE;
    q->size--;
    return removed;
}

int getAverageReleaseYear(CircularQueue *q) {
    if (isEmpty(q)) return 0;
    
    int sum = 0, count = 0;
    for (int i = 0; i < q->size; i++) {
        int index = (q->front + i) % MAX_SIZE;
        if (q->data[index].release_year > 0) {
            sum += q->data[index].release_year;
            count++;
        }
    }
    return count > 0 ? (int)round((float)sum / count) : 0;
}


void removeQuotesAndTrim(char *str) {
    int len = strlen(str);
    if (len > 0 && str[0] == '"') {
        memmove(str, str + 1, len);
        len--;
    }
    if (len > 0 && str[len - 1] == '"') {
        str[len - 1] = '\0';
        len--;
    }
    
    // Trim leading spaces
    while (len > 0 && str[0] == ' ') {
        memmove(str, str + 1, len);
        len--;
    }
    
    // Trim trailing spaces
    while (len > 0 && str[len - 1] == ' ') {
        str[len - 1] = '\0';
        len--;
    }
    
    if (len == 0) {
        strcpy(str, "NaN");
    }
}

void splitAndSort(char *src, char dest[][100], int *count) {
    char temp[1000];
    strcpy(temp, src);
    removeQuotesAndTrim(temp);
    
    if (strcmp(temp, "NaN") == 0) {
        strcpy(dest[0], "NaN");
        *count = 1;
        return;
    }
    
    *count = 0;
    char *token = strtok(temp, ",");
    while (token != NULL && *count < MAX_CAST) {
        removeQuotesAndTrim(token);
        strcpy(dest[*count], token);
        (*count)++;
        token = strtok(NULL, ",");
    }
    
    // Simple bubble sort
    for (int i = 0; i < *count - 1; i++) {
        for (int j = i + 1; j < *count; j++) {
            if (strcmp(dest[i], dest[j]) > 0) {
                char tmp[100];
                strcpy(tmp, dest[i]);
                strcpy(dest[i], dest[j]);
                strcpy(dest[j], tmp);
            }
        }
    }
}

void parseCsvLine(char *line, Show *show) {
    char *tokens[20];
    int tokenCount = 0;
    
    char *ptr = line;
    int inQuotes = 0;
    char *start = ptr;
    
    while (*ptr) {
        if (*ptr == '"') {
            inQuotes = !inQuotes;
        } else if (*ptr == ',' && !inQuotes) {
            *ptr = '\0';
            tokens[tokenCount] = start;
            tokenCount++;
            start = ptr + 1;
        }
        ptr++;
    }
    tokens[tokenCount] = start;
    tokenCount++;
    
    // Preencher o show
    strcpy(show->show_id, tokens[0]);
    removeQuotesAndTrim(show->show_id);
    
    strcpy(show->type, tokens[1]);
    removeQuotesAndTrim(show->type);
    
    strcpy(show->title, tokens[2]);
    removeQuotesAndTrim(show->title);
    
    strcpy(show->director, tokens[3]);
    removeQuotesAndTrim(show->director);
    
    // Cast
    splitAndSort(tokens[4], show->cast, &show->cast_count);
    
    strcpy(show->country, tokens[5]);
    removeQuotesAndTrim(show->country);
    
    strcpy(show->date_added, tokens[6]);
    removeQuotesAndTrim(show->date_added);
    
    // Release year
    char yearStr[100];
    strcpy(yearStr, tokens[7]);
    removeQuotesAndTrim(yearStr);
    show->release_year = (strcmp(yearStr, "NaN") == 0) ? 0 : atoi(yearStr);
    
    strcpy(show->rating, tokens[8]);
    removeQuotesAndTrim(show->rating);
    
    strcpy(show->duration, tokens[9]);
    removeQuotesAndTrim(show->duration);
    
    // Listed in
    splitAndSort(tokens[10], show->listed_in, &show->listed_count);
}

Show buscarPorShowId(const char *filename, const char *idProcurado) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        perror("Erro ao abrir arquivo");
        exit(1);
    }
    
    char line[1000];
    fgets(line, sizeof(line), file); // Ignora cabeçalho
    
    while (fgets(line, sizeof(line), file)) {
        Show show;
        parseCsvLine(line, &show);
        
        if (strcmp(show.show_id, idProcurado) == 0) {
            fclose(file);
            return show;
        }
    }
    
    fclose(file);
    Show empty;
    memset(&empty, 0, sizeof(Show));
    return empty;
}

void inserirNaFila(CircularQueue *fila, Show show) {
    if (isFull(fila)) {
        Show removido = dequeue(fila);
    }
    enqueue(fila, show);
    printf("[Media] %d\n", getAverageReleaseYear(fila));
}

int main() {
    CircularQueue fila;
    initQueue(&fila);
    const char *path = "/tmp/disneyplus.csv";
    
    char input[100];
    while (1) {
        fgets(input, sizeof(input), stdin);
        input[strcspn(input, "\n")] = '\0'; // Remove newline
        
        if (strcmp(input, "FIM") == 0) {
            break;
        }
        
        Show show = buscarPorShowId(path, input);
        if (strlen(show.show_id) > 0) {
            inserirNaFila(&fila, show);
        }
    }
    
    int n;
    scanf("%d", &n);
    getchar(); // Consume newline
    
    for (int i = 0; i < n; i++) {
        fgets(input, sizeof(input), stdin);
        input[strcspn(input, "\n")] = '\0';
        
        if (input[0] == 'I') {
            char *id = input + 2; // pula "I "
            Show show = buscarPorShowId(path, id);
            if (strlen(show.show_id) > 0) {
                inserirNaFila(&fila, show);
            }
        } else if (input[0] == 'R') {
            if (!isEmpty(&fila)) {
                Show removido = dequeue(&fila);
                printf("(R) %s\n", removido.title);
            }
        }
    }
    // Exibe os títulos restantes na fila
        for (int i = 0; i < fila.size; i++) {
            int index = (fila.front + i) % MAX_SIZE;
            printf("%s\n", fila.data[index].title);
        }

    return 0;
}