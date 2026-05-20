#include <stdio.h>
#include <stdlib.h>

// Definição da estrutura da Lista Linear
typedef struct {
    int *lista;      // Vetor dinâmico para armazenar os elementos
    int tamanho;     // Número atual de elementos na lista
    int capacidade;  // Capacidade máxima da lista
} ListaLinear;

// Cria e inicializa a lista com capacidade máxima `max`
ListaLinear* newListaLinear(int max) {
    ListaLinear *novaLista = (ListaLinear*) malloc(sizeof(ListaLinear));
    if (novaLista == NULL) {
        printf("Erro: Falha na alocação da estrutura.\n");
        exit(1);
    }

    novaLista->lista = (int*) malloc(max * sizeof(int));
    if (novaLista->lista == NULL) {
        printf("Erro: Falha na alocação do array de elementos.\n");
        free(novaLista);
        exit(1);
    }

    novaLista->tamanho = 0;
    novaLista->capacidade = max;
    return novaLista;
}

// Libera a memória alocada para a lista
void delListaLinear(ListaLinear* lista) {
    if (lista != NULL) {
        free(lista->lista);
        free(lista);
    }
}

// Insere um elemento no início da lista
void inserirInicio(ListaLinear* lista, int elem) {
    if (lista->tamanho == lista->capacidade) {
        printf("Overflow: Lista cheia\n");
        return;
    }

    for (int i = lista->tamanho; i > 0; i--) {
        lista->lista[i] = lista->lista[i - 1];
    }

    lista->lista[0] = elem;
    lista->tamanho++;
}

// Insere um elemento no final da lista
void inserirFim(ListaLinear* lista, int elem) {
    if (lista->tamanho == lista->capacidade) {
        printf("Overflow: Lista cheia\n");
        return;
    }

    lista->lista[lista->tamanho] = elem;
    lista->tamanho++;
}

// Insere um elemento em uma posição específica
void inserir(ListaLinear* lista, int elem, int pos) {
    if (lista->tamanho == lista->capacidade) {
        printf("Overflow: Lista cheia\n");
        return;
    }
    if (pos < 0 || pos > lista->tamanho) {
        printf("Erro: Posição inválida\n");
        return;
    }

    for (int i = lista->tamanho; i > pos; i--) {
        lista->lista[i] = lista->lista[i - 1];
    }

    lista->lista[pos] = elem;
    lista->tamanho++;
}

// Remove e retorna o elemento do início da lista
int removerInicio(ListaLinear* lista) {
    if (lista->tamanho == 0) {
        printf("Underflow: Lista vazia\n");
        return -1;
    }

    int removido = lista->lista[0];

    for (int i = 0; i < lista->tamanho - 1; i++) {
        lista->lista[i] = lista->lista[i + 1];
    }

    lista->tamanho--;
    return removido;
}

// Remove e retorna o elemento do final da lista
int removerFim(ListaLinear* lista) {
    if (lista->tamanho == 0) {
        printf("Underflow: Lista vazia\n");
        return -1;
    }

    lista->tamanho--;
    return lista->lista[lista->tamanho];
}

// Remove e retorna o elemento de uma posição específica
int remover(ListaLinear* lista, int pos) {
    if (lista->tamanho == 0) {
        printf("Underflow: Lista vazia\n");
        return -1;
    }
    if (pos < 0 || pos >= lista->tamanho) {
        printf("Erro: Posição inválida\n");
        return -1;
    }

    int removido = lista->lista[pos];

    for (int i = pos; i < lista->tamanho - 1; i++) {
        lista->lista[i] = lista->lista[i + 1];
    }

    lista->tamanho--;
    return removido;
}

// Mostra os elementos da lista
void mostrar(ListaLinear* lista) {
    if (lista->tamanho == 0) {
        printf("Lista vazia\n");
        return;
    }

    for (int i = 0; i < lista->tamanho; i++) {
        printf("%d ", lista->lista[i]);
    }
    printf("\n");
}

// Pesquisa um elemento na lista e retorna 1 se encontrado, 0 caso contrário
int pesquisar(ListaLinear* lista, int elem) {
    for (int i = 0; i < lista->tamanho; i++) {
        if (lista->lista[i] == elem) {
            return 1;
        }
    }
    return 0;
}

// Função principal para testar as operações
int main() {
    ListaLinear* lista = newListaLinear(5);

    inserirFim(lista, 10);
    inserirFim(lista, 20);
    inserirInicio(lista, 5);
    inserir(lista, 15, 2);

    printf("Lista atual: ");
    mostrar(lista); // Esperado: 5 10 15 20

    printf("Removendo do início: %d\n", removerInicio(lista));
    mostrar(lista); // Esperado: 10 15 20

    printf("Removendo do fim: %d\n", removerFim(lista));
    mostrar(lista); // Esperado: 10 15

    printf("Removendo da posição 1: %d\n", remover(lista, 1));
    mostrar(lista); // Esperado: 10

    printf("Pesquisando 10: %s\n", pesquisar(lista, 10) ? "Encontrado" : "Não encontrado");
    printf("Pesquisando 20: %s\n", pesquisar(lista, 20) ? "Encontrado" : "Não encontrado");

    delListaLinear(lista); // Libera a memória alocada

    return 0;
}
