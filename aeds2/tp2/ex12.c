#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

// --- Estruturas ---
typedef struct { int hora; int minuto; } Hora;
typedef struct { int dia; int mes; int ano; } Data;

typedef struct {
    int id;
    char* nome;
    char* cidade;
    int capacidade;
    double avaliacao;
    int n_tipos_cozinha;
    char** tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    char* aberto;
} Restaurante;

typedef struct {
    int tamanho;
    Restaurante** restaurantes;
} Colecao_Restaurantes;

// --- Estrutura da Pilha Flexível ---
typedef struct Celula {
    Restaurante* restaurante;
    struct Celula* prox;
} Celula;

typedef struct {
    Celula* topo;
} Pilha;

// --- Funções da Pilha ---
Pilha* criar_pilha() {
    Pilha* p = (Pilha*)malloc(sizeof(Pilha));
    p->topo = NULL;
    return p;
}

void empilhar(Pilha* p, Restaurante* r, long* mov) {
    Celula* nova = (Celula*)malloc(sizeof(Celula));
    nova->restaurante = r;
    nova->prox = p->topo;
    p->topo = nova;
    (*mov)++;
}

Restaurante* desempilhar(Pilha* p, long* mov) {
    if (p->topo == NULL) return NULL;
    Celula* temp = p->topo;
    Restaurante* r = temp->restaurante;
    p->topo = temp->prox;
    free(temp);
    (*mov)++;
    return r;
}

// --- Parsing e Suporte ---
Data parse_data(char* data) {
    Data d;
    sscanf(data, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

Hora parse_hora(char* hora_str) {
    Hora h;
    sscanf(hora_str, "%d:%d", &h.hora, &h.minuto);
    return h;
}

Restaurante* parse_restaurante(char* str) {
    int id, cap; double aval;
    char nome[100], cid[100], tipos[200], preco_s[10], h_ab[10], h_fe[10], d_s[15], ab_s[10];
    sscanf(str, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%9[^-]-%9[^,],%14[^,],%9s", 
           &id, nome, cid, &cap, &aval, tipos, preco_s, h_ab, h_fe, d_s, ab_s);

    Restaurante *r = malloc(sizeof(Restaurante));
    r->id = id;
    r->nome = strdup(nome);
    r->cidade = strdup(cid);
    r->capacidade = cap;
    r->avaliacao = aval;
    r->data_abertura = parse_data(d_s);
    r->horario_abertura = parse_hora(h_ab);
    r->horario_fechamento = parse_hora(h_fe);
    r->aberto = strdup(ab_s);
    r->tipos_cozinha = malloc(sizeof(char*));
    r->tipos_cozinha[0] = strdup(tipos);
    r->n_tipos_cozinha = 1;
    int c = 0; for(int i=0; preco_s[i]; i++) if(preco_s[i]=='$') c++;
    r->faixa_preco = c;
    return r;
}

void imprimir_restaurante(Restaurante* r) {
    char p[5] = ""; for(int i=0; i<r->faixa_preco; i++) strcat(p, "$");
    char t1[100] = {0}, t2[100] = {0};
    sscanf(r->tipos_cozinha[0], "%99[^;];%99s", t1, t2);
    printf("[%d ## %s ## %s ## %d ## %.1f ## [%s,%s] ## %s ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]\n",
           r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, t1, t2, p,
           r->horario_abertura.hora, r->horario_abertura.minuto, r->horario_fechamento.hora, r->horario_fechamento.minuto,
           r->data_abertura.dia, r->data_abertura.mes, r->data_abertura.ano, r->aberto);
}

void ler_csv_colecao(Colecao_Restaurantes* col, char* path) {
    FILE* csv = fopen(path, "r");
    if (!csv) return;
    char linha[1024]; fgets(linha, 1024, csv);
    while (fgets(linha, 1024, csv)) {
        linha[strcspn(linha, "\r\n")] = 0;
        col->restaurantes[col->tamanho++] = parse_restaurante(linha);
    }
    fclose(csv);
}

int main() {
    Colecao_Restaurantes* colecao = malloc(sizeof(Colecao_Restaurantes));
    colecao->tamanho = 0;
    colecao->restaurantes = malloc(5000 * sizeof(Restaurante*));
    ler_csv_colecao(colecao, "/tmp/restaurantes.csv");

    Pilha* pilha = criar_pilha();
    long comp = 0, mov = 0;
    clock_t inicio, fim;
    int id_busca;

    // IDs Iniciais
    while (scanf("%d", &id_busca) && id_busca != -1) {
        for (int i = 0; i < colecao->tamanho; i++) {
            comp++;
            if (colecao->restaurantes[i]->id == id_busca) {
                empilhar(pilha, colecao->restaurantes[i], &mov);
                break;
            }
        }
    }

    inicio = clock();
    int num_comandos;
    scanf("%d", &num_comandos);
    char comando[2];

    for (int i = 0; i < num_comandos; i++) {
        scanf("%s", comando);
        if (comando[0] == 'I') {
            scanf("%d", &id_busca);
            for (int j = 0; j < colecao->tamanho; j++) {
                comp++;
                if (colecao->restaurantes[j]->id == id_busca) {
                    empilhar(pilha, colecao->restaurantes[j], &mov);
                    break;
                }
            }
        } else if (comando[0] == 'R') {
            Restaurante* r = desempilhar(pilha, &mov);
            if (r) printf("(R)%s\n", r->nome);
        }
    }
    fim = clock();

    // Impressão Final da Pilha
    Celula* atual = pilha->topo;

    while (atual) {

        imprimir_restaurante(atual->restaurante);
        atual = atual->prox;
    }

    // Log
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    FILE* log = fopen("850602_pilha.txt", "w");
    if (log) {
        fprintf(log, "850602\t%ld\t%ld\t%lf\n", comp, mov, tempo);
        fclose(log);
    }

    return 0;
}