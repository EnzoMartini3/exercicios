#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

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

    int nTiposCozinha;
    char** tiposCozinha;

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

void formatar_hora(Hora* hora, char* buffer) {
    sprintf(buffer, "%d:%d", hora->hora, hora->minuto);
}

void formatar_data(Data* data, char* buffer) {
    sprintf(buffer, "%d/%d/%d", data->ano, data->mes, data->dia);
}

Restaurante* parse_restaurante(char* str) {

    int id;
    
	char nome[100];    
	char cidade[100];
	int capacidade;
	double avaliacao;
    
	char tiposCozinha[200];
	char char_faixa_preco[10];
    
	char hora_abertura_str[10];
	char hora_fechamento_str[10];
	char data_str[15];
    
	int faixa_preco;
	char char_Aberto[10];


    sscanf(str, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%9[^-]-%9[^,],%14[^,],%9s", 
        &id, nome, cidade, &capacidade, &avaliacao, tiposCozinha, 
        char_faixa_preco, hora_abertura_str, hora_fechamento_str,
        data_str, char_Aberto);


    Restaurante *restaurante = malloc(sizeof(Restaurante));

    restaurante->id = id;

    int tamanho_nome = 0;
    while (nome[tamanho_nome] != '\0') {
        tamanho_nome++;
    }
    restaurante->nome = malloc(tamanho_nome + 1);
    for (int i = 0; i <= tamanho_nome; i++) {
        restaurante->nome[i] = nome[i];
    }

    int tamanho_cidade = 0;
    while (cidade[tamanho_cidade] != '\0') {
        tamanho_cidade++;
    }
    restaurante->cidade = malloc(tamanho_cidade + 1);
    for (int i = 0; i <= tamanho_cidade; i++) {
        restaurante->cidade[i] = cidade[i];
    }

    restaurante->capacidade = capacidade;
    restaurante->avaliacao = avaliacao;

    restaurante->aberto = char_Aberto;
    restaurante->data_abertura = parse_data(data_str);
    restaurante->horario_abertura = parse_hora(hora_abertura_str);
    restaurante->horario_fechamento = parse_hora(hora_fechamento_str);
    restaurante->tiposCozinha = malloc(1 * sizeof(char*));

    int tamanho_tipos = 0;
    while (tiposCozinha[tamanho_tipos] != '\0') {
        tamanho_tipos++;
    }
    restaurante->tiposCozinha[0] = malloc(tamanho_tipos + 1);
    for (int i = 0; i <= tamanho_tipos; i++) {
        restaurante->tiposCozinha[0][i] = tiposCozinha[i];
    }
    restaurante->nTiposCozinha = 1;

    int tamanho_aberto = 0;
    while (char_Aberto[tamanho_aberto] != '\0') {
        tamanho_aberto++;
    }

    restaurante->aberto = malloc(tamanho_aberto + 1);
    for (int i = 0; i <= tamanho_aberto; i++) {
        restaurante->aberto[i] = char_Aberto[i];
    }

    int conta_cifrao = 0;
    for (int i = 0; char_faixa_preco[i] != '\0'; i++) {
        if (char_faixa_preco[i] == '$') {
            conta_cifrao++;
        }
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

        linha[strcspn(linha, "\r\n")] = 0;
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

void formatar_restaurante(Restaurante* restaurante, char* restaurantes) {

    char char_preco [5];
    int contador;
    for (contador = 0; contador < restaurante-> faixa_preco; contador++){
        
        char_preco[contador] = '$';

    }

    char tipo1 [50];
    char tipo2 [50];
    
    sscanf(restaurante->tiposCozinha[0], "%99[^;];%99s", tipo1, tipo2);

    char_preco[contador] = '\0';

    char* status_string;

    if (strcmp(restaurante->aberto, "true") == 0) {
        status_string = "true";
    } else {
        status_string = "false";
}

    sprintf(restaurantes, "[%d ## %s ## %s ## %d ## %.1f ## [%s,%s] ## %s ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]", restaurante->id, restaurante->nome, 
        restaurante->cidade, restaurante->capacidade, restaurante->avaliacao,tipo1,tipo2, char_preco ,restaurante->horario_abertura.hora,restaurante->horario_abertura.minuto,
        restaurante->horario_fechamento.hora,restaurante->horario_fechamento.minuto,restaurante->data_abertura.dia, restaurante->data_abertura.mes,
    restaurante->data_abertura.ano, status_string);
}


void imprimir_restaurante(Restaurante* restaurante) {

    char temp[300];

    formatar_restaurante(restaurante, temp);
    printf("%s\n", temp);

}  

void Exercicio_01(Colecao_Restaurantes* colecao){

    int busca_ID;
    scanf ("%d", &busca_ID);

    while (busca_ID != -1){

        imprimir_restaurante (colecao->restaurantes[busca_ID - 1]);

    scanf("%d", &busca_ID);


    }


}


int main() {

    Colecao_Restaurantes* colecao = ler_csv();

    
    Exercicio_01(colecao);


	//Free
    for (int i = 0; i < (colecao->tamanho); i++) {

        free(colecao->restaurantes[i]->nome);
        free(colecao->restaurantes[i]->cidade);

        for (int j = 0; j < (colecao->restaurantes[i]->nTiposCozinha); j++) {
            free(colecao->restaurantes[i]->tiposCozinha[j]);
        }

        free(colecao->restaurantes[i]->tiposCozinha);
        free(colecao->restaurantes[i]);
    }

    free(colecao->restaurantes);
    free(colecao);

    return 0;
}