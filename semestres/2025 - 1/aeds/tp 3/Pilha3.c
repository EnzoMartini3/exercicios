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

// Utilitários
void remove_quotes_and_trim(char *str) {
    if (str[0] == '"') memmove(str, str + 1, strlen(str));
    int len = strlen(str);
    if (len > 0 && str[len - 1] == '"') str[len - 1] = '\0';
    int start = 0;
    while (isspace((unsigned char)str[start])) start++;
    if (start > 0) memmove(str, str + start, strlen(str + start) + 1);
    char *end = str + strlen(str) - 1;
    while (end > str && isspace(*end)) *end-- = '\0';
    if (strlen(str) == 0) strcpy(str, "NaN");
}

void split_and_sort(const char *src, char dest[20][100], int *count) {
    char temp[1000];
    strcpy(temp, src);
    *count = 0;
    remove_quotes_and_trim(temp);

    // Adição aqui: se for "NaN", ainda adiciona "NaN" na lista
    if (strcmp(temp, "NaN") == 0) {
        strcpy(dest[0], "NaN");
        *count = 1;
        return;
    }

    char *token = strtok(temp, ",");
    while (token && *count < 20) {
        remove_quotes_and_trim(token);
        strcpy(dest[(*count)++], token);
        token = strtok(NULL, ",");
    }

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
    int colunas = parse_csv_line(linha, tokens);
    *s = new_show();

    // Preencher todos os campos
    for (int i = 0; i < colunas; i++) {
        remove_quotes_and_trim(tokens[i]);
        if (strlen(tokens[i]) == 0) strcpy(tokens[i], "NaN");
    }

    strcpy(s->_show_id, tokens[0]);
    strcpy(s->_type, tokens[1]);
    strcpy(s->_title, tokens[2]);
    strcpy(s->_director, tokens[3]);

    // Cast (lista)
    if (strcmp(tokens[4], "NaN") != 0)
        split_and_sort(tokens[4], s->_cast, &s->_cast_count);
    else
        s->_cast_count = 0;

    strcpy(s->_country, tokens[5]);
    strcpy(s->_date_added, tokens[6]);

    // release_year (int)
    if (strcmp(tokens[7], "NaN") != 0)
        s->_release_year = atoi(tokens[7]);
    else
        s->_release_year = 0;

    strcpy(s->_rating, tokens[8]);
    strcpy(s->_duration, tokens[9]);

    // Listed_in (lista)
    if (strcmp(tokens[10], "NaN") != 0)
        split_and_sort(tokens[10], s->_listed_in, &s->_listed_count);
    else
        s->_listed_count = 0;
}


void imprimir_show(const Show *s, int ni) {
    printf("[%i] => %s ## %s ## %s ## %s ## [", ni, s->_show_id, s->_title, s->_type, s->_director);
    ni--;
    if(s->_cast[0]==NULL){
        printf("NaN");
    }
    for (int i = 0; i < s->_cast_count; i++) {
        printf("%s", s->_cast[i]);
        if (i < s->_cast_count - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->_country, s->_date_added, s->_release_year, s->_rating, s->_duration);
    if(s->_listed_in[0]==NULL){
        printf("NaN");
    }
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
        remove_quotes_and_trim(tokens[0]);
        if (strcmp(tokens[0], id_procurado) == 0) {
            ler_show(resultado, linha);
            fclose(fp);
            return 1;
        }
    }
    fclose(fp);
    return 0;
}

// Lista encadeada

typedef struct Celula {
    Show show;
    struct Celula* prox;
} Celula;

Celula* nova_celula(Show show) {
    Celula* c = (Celula*)malloc(sizeof(Celula));
    c->show = show;
    c->prox = NULL;
    return c;
}

typedef struct {
    Celula* prim;
    Celula* ult;
} ListaSeq1;

void iniciar_lista(ListaSeq1* l) {
    l->prim = l->ult = NULL;
}

void inserir_inicio(ListaSeq1* l, Show show) {
    Celula* nova = nova_celula(show);
    nova->prox = l->prim;
    l->prim = nova;
    if (l->ult == NULL) l->ult = nova;
}

void inserir_fim(ListaSeq1* l, Show show) {
    Celula* nova = nova_celula(show);
    if (l->ult != NULL) l->ult->prox = nova;
    else l->prim = nova;
    l->ult = nova;
}

void inserir_pos(ListaSeq1* l, Show show, int pos) {
    if (pos == 0) {
        inserir_inicio(l, show);
        return;
    }
    Celula* ant = l->prim;
    for (int i = 0; i < pos - 1 && ant != NULL; i++) ant = ant->prox;
    if (ant == NULL) return;
    Celula* nova = nova_celula(show);
    nova->prox = ant->prox;
    ant->prox = nova;
    if (nova->prox == NULL) l->ult = nova;
}

Show remover_inicio(ListaSeq1* l) {
    Show removido = new_show();
    if (l->prim == NULL) return removido;
    Celula* tmp = l->prim;
    removido = tmp->show;
    l->prim = tmp->prox;
    if (l->prim == NULL) l->ult = NULL;
    free(tmp);
    return removido;
}

Show remover_fim(ListaSeq1* l) {
    Show removido = new_show();
    if (l->prim == NULL) return removido;
    if (l->prim == l->ult) {
        removido = l->prim->show;
        free(l->prim);
        l->prim = l->ult = NULL;
        return removido;
    }
    Celula* aux = l->prim;
    while (aux->prox != l->ult) aux = aux->prox;
    removido = l->ult->show;
    free(l->ult);
    l->ult = aux;
    aux->prox = NULL;
    return removido;
}

Show remover_pos(ListaSeq1* l, int pos) {
    if (pos == 0) return remover_inicio(l);
    Celula* ant = l->prim;
    for (int i = 0; i < pos - 1 && ant != NULL; i++) ant = ant->prox;
    if (ant == NULL || ant->prox == NULL) return new_show();
    Celula* tmp = ant->prox;
    Show removido = tmp->show;
    ant->prox = tmp->prox;
    if (tmp == l->ult) l->ult = ant;
    free(tmp);
    return removido;
}

void mostrar_lista(ListaSeq1* l, int ni) {
    for (Celula* i = l->prim; i != NULL; i = i->prox) {
        imprimir_show(&i->show, ni);
    }
}
int main() {
    ListaSeq1 lista;
    iniciar_lista(&lista);
    char path[] = "/tmp/disneyplus.csv";
    char linha[500];
    Show temp;
    int ni = 0;

    // Leitura inicial
    while (fgets(linha, sizeof(linha), stdin)) {
        linha[strcspn(linha, "\n")] = '\0';
        if (strcmp(linha, "FIM") == 0) break;
        if (buscar_por_show_id(path, linha, &temp)) {
            inserir_inicio(&lista, temp);
            ni++;
        }
    }

    int n;
    scanf("%d\n", &n);
    for (int i = 0; i < n; i++) {
        fgets(linha, sizeof(linha), stdin);
        linha[strcspn(linha, "\n")] = '\0';
        char comando[10], arg1[100], arg2[100];
        int pos;

        if (sscanf(linha, "%s %s", comando, arg1) >= 1) {
            if (strcmp(comando, "I") == 0) {
                if (buscar_por_show_id(path, arg1, &temp)) {
                    inserir_inicio(&lista, temp);
                    ni++;
                }
            } else if (strcmp(comando, "R") == 0) {
                Show r = remover_inicio(&lista); 
                printf("(R) %s\n", r._title);
                ni--;
            }
        }
    }

    Celula* atual = lista.prim;
    int contador = ni - 1;
    while (atual != NULL) {
        imprimir_show(&atual->show, contador);
        atual = atual->prox;
        contador--;
    }

    return 0;
}