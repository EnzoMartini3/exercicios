#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

#define TAM_TAB 31
#define TAM_RES 19
#define TAM_TOTAL (TAM_TAB + TAM_RES)

typedef struct {
    int hora;
    int minuto;
} Hora;

typedef struct {
    int dia;
    int mes;
    int ano;
} Data;

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

typedef struct {
    Restaurante* tabela[TAM_TOTAL];
    int reserva_atual;
} HashReserva;

void inicializar_hash(HashReserva* h) {
    for (int i = 0; i < TAM_TOTAL; i++) {
        h->tabela[i] = NULL;
    }
    h->reserva_atual = TAM_TAB;
}

int calcular_hash(char* nome) {
    int soma = 0;
    for (int i = 0; nome[i] != '\0'; i++) {
        soma += (int)nome[i];
    }
    return soma % TAM_TAB;
}

void inserir_hash(HashReserva* h, Restaurante* r) {
    int pos = calcular_hash(r->nome);
    if (h->tabela[pos] == NULL) {
        h->tabela[pos] = r;
    } else {
        if (h->reserva_atual < TAM_TOTAL) {
            h->tabela[h->reserva_atual] = r;
            h->reserva_atual++;
        } else {
            printf("%s\n", r->nome);
        }
    }
}

int pesquisar_hash(HashReserva* h, char* nome_busca, long long* comparacoes) {
    int pos = calcular_hash(nome_busca);
    (*comparacoes)++;
    if (h->tabela[pos] != NULL && strcmp(h->tabela[pos]->nome, nome_busca) == 0) {
        return pos;
    }
    for (int i = TAM_TAB; i < h->reserva_atual; i++) {
        (*comparacoes)++;
        if (h->tabela[i] != NULL && strcmp(h->tabela[i]->nome, nome_busca) == 0) {
            return i;
        }
    }
    return -1;
}

void formatar_restaurante(Restaurante* restaurante, char* buffer) {
    char char_preco[6];
    int contador;
    for (contador = 0; contador < restaurante->faixa_preco && contador < 5; contador++){
        char_preco[contador] = '$';
    }
    char_preco[contador] = '\0';
    char tipo1[100] = {0};
    char tipo2[100] = {0};
    sscanf(restaurante->tipos_cozinha[0], "%99[^;];%99s", tipo1, tipo2);
    char* status_string = (strcmp(restaurante->aberto, "true") == 0) ? "true" : "false";
    sprintf(buffer, "[%d ## %s ## %s ## %d ## %.1f ## [%s,%s] ## %s ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]", 
        restaurante->id, restaurante->nome, restaurante->cidade, restaurante->capacidade, restaurante->avaliacao,
        tipo1, tipo2, char_preco, restaurante->horario_abertura.hora, restaurante->horario_abertura.minuto,
        restaurante->horario_fechamento.hora, restaurante->horario_fechamento.minuto,
        restaurante->data_abertura.dia, restaurante->data_abertura.mes, restaurante->data_abertura.ano, status_string);
}

Data parse_data(char* data) {
    Data d_ata;
    sscanf(data, "%d-%d-%d", &d_ata.ano, &d_ata.mes, &d_ata.dia);
    return d_ata;
}

Hora parse_hora(char* hora_str) {
    Hora h_ora;
    sscanf(hora_str, "%d:%d", &h_ora.hora, &h_ora.minuto);
    return h_ora;
}

Restaurante* parse_restaurante(char* str) {
    int id;
    char nome[100];    
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos_cozinha[200];
    char char_faixa_preco[10];
    char hora_abertura_str[10];
    char hora_fechamento_str[10];
    char data_str[15];
    char char_Aberto[10];

    sscanf(str, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%9[^-]-%9[^,],%14[^,],%9s", 
        &id, nome, cidade, &capacidade, &avaliacao, tipos_cozinha, 
        char_faixa_preco, hora_abertura_str, hora_fechamento_str, data_str, char_Aberto);

    Restaurante *restaurante = malloc(sizeof(Restaurante));
    restaurante->id = id;
    int tamanho_nome = strlen(nome);
    restaurante->nome = malloc(tamanho_nome + 1);
    strcpy(restaurante->nome, nome);
    int tamanho_cidade = strlen(cidade);
    restaurante->cidade = malloc(tamanho_cidade + 1);
    strcpy(restaurante->cidade, cidade);
    restaurante->capacidade = capacidade;
    restaurante->avaliacao = avaliacao;
    restaurante->data_abertura = parse_data(data_str);
    restaurante->horario_abertura = parse_hora(hora_abertura_str);
    restaurante->horario_fechamento = parse_hora(hora_fechamento_str);
    restaurante->tipos_cozinha = malloc(1 * sizeof(char*));
    int tamanho_tipos = strlen(tipos_cozinha);
    restaurante->tipos_cozinha[0] = malloc(tamanho_tipos + 1);
    strcpy(restaurante->tipos_cozinha[0], tipos_cozinha);
    restaurante->n_tipos_cozinha = 1;
    int tamanho_aberto = strlen(char_Aberto);
    restaurante->aberto = malloc(tamanho_aberto + 1);
    strcpy(restaurante->aberto, char_Aberto);
    int conta_cifrao = 0;
    for (int i = 0; char_faixa_preco[i] != '\0'; i++) {
        if (char_faixa_preco[i] == '$') conta_cifrao++;
    }
    restaurante->faixa_preco = conta_cifrao;
    return restaurante;
}

void ler_csv_colecao(Colecao_Restaurantes* colecao, char* path) {
    FILE* csv = fopen(path, "r");
    if (!csv) return;
    char linha[1024];
    fgets(linha, 1024, csv);
    while (fgets(linha, 1024, csv)) {
        int i = 0;
        while (linha[i] != '\0') {
            if (linha[i] == '\n' || linha[i] == '\r') {
                linha[i] = '\0';
                break;
            }
            i++;
        }
        colecao->restaurantes[colecao->tamanho] = parse_restaurante(linha);
        colecao->tamanho++;
    }  
    fclose(csv);
}

Colecao_Restaurantes* ler_csv() {
    Colecao_Restaurantes* colecao = malloc(sizeof(Colecao_Restaurantes));
    colecao->tamanho = 0;
    colecao->restaurantes = malloc(5000 * sizeof(Restaurante*));
    ler_csv_colecao(colecao, "/tmp/restaurantes.csv");
    return colecao;
}

Restaurante* buscar_por_id(Colecao_Restaurantes* colecao, int id) {
    for (int i = 0; i < colecao->tamanho; i++) {
        if (colecao->restaurantes[i]->id == id) {
            return colecao->restaurantes[i];
        }
    }
    return NULL;
}

int main() {
    Colecao_Restaurantes* colecao = ler_csv();
    HashReserva hash_tabela;
    inicializar_hash(&hash_tabela);
    char entrada[150];
    long long qntd_comparacoes = 0;

    while (scanf("%s", entrada) == 1) {
        if (strcmp(entrada, "-1") == 0) break;
        int id = atoi(entrada);
        Restaurante* r = buscar_por_id(colecao, id);
        if (r != NULL) {
            inserir_hash(&hash_tabela, r);
        }
    }

    getchar(); 

    clock_t inicio = clock();

    while (fgets(entrada, sizeof(entrada), stdin)) {
        int i = 0;
        while (entrada[i] != '\0') {
            if (entrada[i] == '\n' || entrada[i] == '\r') {
                entrada[i] = '\0';
                break;
            }
            i++;
        }
        if (strcmp(entrada, "FIM") == 0) break;
        if (strlen(entrada) == 0) continue;

        int pos_encontrada = pesquisar_hash(&hash_tabela, entrada, &qntd_comparacoes);
        if (pos_encontrada != -1) {
            char buffer[500];
            formatar_restaurante(hash_tabela.tabela[pos_encontrada], buffer);
            printf("%d %s\n", pos_encontrada, buffer);
        } else {
            printf("-1\n");
        }
    }

    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    FILE* log = fopen("matrícula_hash_reserva.txt", "w");
    if (log != NULL) {
        fprintf(log, "850602\t%lld\t%.6lf\n", qntd_comparacoes, tempo);
        fclose(log);
    }

    for (int i = 0; i < (colecao->tamanho); i++) {
        free(colecao->restaurantes[i]->nome);
        free(colecao->restaurantes[i]->cidade);
        for (int j = 0; j < (colecao->restaurantes[i]->n_tipos_cozinha); j++) {
            free(colecao->restaurantes[i]->tipos_cozinha[j]);
        }
        free(colecao->restaurantes[i]->tipos_cozinha);
        free(colecao->restaurantes[i]->aberto);
        free(colecao->restaurantes[i]);
    }
    free(colecao->restaurantes);
    free(colecao);

    return 0;
}