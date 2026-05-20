#include <stdio.h>    // Para entrada/saída (printf, scanf, fopen, fclose, etc.)
#include <stdlib.h>   // Para alocação de memória (malloc, free, strdup, atoi)
#include <string.h>   // Para manipulação de strings (strcmp, strlen, strcpy, strcat, strcspn, strsep)
#include <stdbool.h>  // Para tipos booleanos (true, false)
#include <time.h>     // Para medição de tempo (clock, CLOCKS_PER_SEC)
#include <ctype.h>    // Para funções de caracteres (isspace)

// --- Contador global para comparações ---
long long comparisons_count = 0;

// --- Estrutura Show ---
// Representa um item de show com diversos atributos.
typedef struct {
    char *showId;
    char *type;
    char *title;
    char *director;
    char **cast;       // Array de strings para o elenco
    int num_cast;      // Número de membros no elenco
    char *country;
    char *dateAdded;
    int releaseYear;
    char *rating;
    char *duration;
    char **listedIn;   // Array de strings para as categorias
    int num_listedIn;  // Número de categorias
} Show;

// --- Declarações de Funções (Forward Declarations) ---
// Para que as funções possam se chamar mutualmente antes de serem definidas.
void freeShow(Show *s);
char** parse_and_sort_string_array(char* field_str, int* num_elements);
char* trim_whitespace(char* str);
void initShow(Show *s);
char** dividirCSV(char* line, int* num_parts);
void free_csv_parts(char** parts, int num_parts);
Show* lerShow(const char* targetId, const char* pathCSV);
void imprimirShow(const Show *s);

// --- Funções Utilitárias para a Estrutura Show ---

/**
 * @brief Inicializa uma estrutura Show com valores padrão "NaN" ou 0.
 * @param s Ponteiro para a estrutura Show a ser inicializada.
 */
void initShow(Show *s) {
    s->showId = strdup("NaN");
    s->type = strdup("NaN");
    s->title = strdup("NaN");
    s->director = strdup("NaN");
    s->cast = NULL;
    s->num_cast = 0;
    s->country = strdup("NaN");
    s->dateAdded = strdup("NaN");
    s->releaseYear = 0;
    s->rating = strdup("NaN");
    s->duration = strdup("NaN");
    s->listedIn = NULL;
    s->num_listedIn = 0;
}

/**
 * @brief Libera toda a memória alocada dinamicamente para uma estrutura Show.
 * @param s Ponteiro para a estrutura Show a ser liberada.
 */
void freeShow(Show *s) {
    if (s) {
        free(s->showId);
        free(s->type);
        free(s->title);
        free(s->director);
        if (s->cast) {
            for (int i = 0; i < s->num_cast; i++) {
                free(s->cast[i]);
            }
            free(s->cast);
        }
        free(s->country);
        free(s->dateAdded);
        free(s->rating);
        free(s->duration);
        if (s->listedIn) {
            for (int i = 0; i < s->num_listedIn; i++) {
                free(s->listedIn[i]);
            }
            free(s->listedIn);
        }
        free(s); // Libera a própria estrutura Show, se foi alocada dinamicamente
    }
}

/**
 * @brief Função de comparação para qsort, usada para ordenar arrays de strings.
 * @param a Primeiro ponteiro para string.
 * @param b Segundo ponteiro para string.
 * @return Resultado da comparação lexicográfica.
 */
int compareStrings(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

/**
 * @brief Remove espaços em branco do início e do final de uma string.
 * @param str A string a ser processada.
 * @return Um ponteiro para a string modificada (a mesma string de entrada).
 */
char* trim_whitespace(char* str) {
    if (str == NULL) return NULL;
    char *end;

    // Remove espaços do início
    while(isspace((unsigned char)*str)) str++;

    if(*str == 0)  // Se a string ficou vazia (só tinha espaços)
        return str;

    // Remove espaços do final
    end = str + strlen(str) - 1;
    while(end > str && isspace((unsigned char)*end)) end--;

    // Adiciona terminador nulo
    *(end + 1) = 0;

    return str;
}

/**
 * @brief Converte uma string separada por vírgulas em um array de strings ordenado.
 * @param field_str A string contendo os elementos separados por vírgulas.
 * @param num_elements Ponteiro para armazenar o número de elementos no array resultante.
 * @return Um array de strings alocado dinamicamente, ou NULL se a string de entrada for vazia/NaN ou houver erro de alocação.
 */
char** parse_and_sort_string_array(char* field_str, int* num_elements) {
    *num_elements = 0;
    if (field_str == NULL || strlen(field_str) == 0 || strcmp(field_str, "NaN") == 0) {
        return NULL;
    }

    char* temp_str = strdup(field_str); // Duplica a string, pois strtok/strsep a modificam
    if (temp_str == NULL) { return NULL; } // Erro de alocação

    char** elements = NULL;
    int count = 0;
    char* token;
    char* rest = temp_str; // Ponteiro para o restante da string para strsep

    // strsep é mais seguro que strtok para múltiplos tokens e contextos
    while ((token = strsep(&rest, ",")) != NULL) {
        char* trimmed_token = trim_whitespace(token);
        if (strlen(trimmed_token) > 0) { // Adiciona apenas tokens não vazios
            elements = (char**)realloc(elements, (count + 1) * sizeof(char*));
            if (elements == NULL) {
                // Libera memória alocada em caso de falha de realloc
                for(int i = 0; i < count; i++) free(elements[i]);
                free(elements);
                free(temp_str);
                return NULL;
            }
            elements[count] = strdup(trimmed_token);
            if (elements[count] == NULL) {
                // Libera tudo em caso de falha de strdup
                for(int i = 0; i <= count; i++) free(elements[i]);
                free(elements);
                free(temp_str);
                return NULL;
            }
            count++;
        }
    }
    free(temp_str); // Libera a cópia mutável da string original

    if (count > 0) {
        qsort(elements, count, sizeof(char*), compareStrings); // Ordena o array de strings
    }
    *num_elements = count;
    return elements;
}

/**
 * @brief Divide uma linha CSV em partes, tratando aspas duplas e vírgulas dentro delas.
 * @param line A linha CSV a ser dividida.
 * @param num_parts Ponteiro para armazenar o número de partes resultantes.
 * @return Um array de strings alocado dinamicamente, ou NULL em caso de erro.
 */
char** dividirCSV(char* line, int* num_parts) {
    // A estrutura Show tem 11 campos. Pré-alocamos espaço para 11 ponteiros.
    char** parts = (char**)calloc(11, sizeof(char*));
    if (parts == NULL) { *num_parts = 0; return NULL; } // Erro de alocação

    int current_part_idx = 0;
    int i = 0; // Índice para a linha de entrada
    bool in_quotes = false;
    char temp_field_buffer[2048]; // Buffer temporário para construir cada campo (tamanho razoável)
    int field_buffer_pos = 0;

    // Duplica a linha para que possa ser modificada (pois vamos adicionar \0 para terminar campos)
    char* line_copy = strdup(line);
    if (line_copy == NULL) { free(parts); *num_parts = 0; return NULL; }

    int line_len = strlen(line_copy);

    for (i = 0; i < line_len && current_part_idx < 11; i++) {
        if (line_copy[i] == '\"') {
            in_quotes = !in_quotes; // Alterna o estado de estar dentro de aspas
        } else if (line_copy[i] == ',' && !in_quotes) {
            // Se encontrou uma vírgula fora das aspas, o campo atual terminou
            temp_field_buffer[field_buffer_pos] = '\0'; // Termina o campo no buffer
            parts[current_part_idx] = strdup(temp_field_buffer); // Duplica o campo para o array de partes
            if (parts[current_part_idx] == NULL) {
                // Libera memória alocada anteriormente em caso de falha
                for (int k = 0; k < current_part_idx; k++) free(parts[k]);
                free(parts);
                free(line_copy);
                *num_parts = 0;
                return NULL;
            }
            current_part_idx++;
            field_buffer_pos = 0; // Reinicia o buffer para o próximo campo
            memset(temp_field_buffer, 0, sizeof(temp_field_buffer)); // Limpa o buffer
        } else {
            // Adiciona o caractere ao buffer do campo atual
            if (field_buffer_pos < sizeof(temp_field_buffer) - 1) { // Previne overflow do buffer
                temp_field_buffer[field_buffer_pos++] = line_copy[i];
            }
        }
    }

    // Adiciona a última parte após o loop (pois não termina com vírgula)
    if (current_part_idx < 11) {
        temp_field_buffer[field_buffer_pos] = '\0';
        parts[current_part_idx] = strdup(temp_field_buffer);
        if (parts[current_part_idx] == NULL) {
            for (int k = 0; k < current_part_idx; k++) free(parts[k]);
            free(parts);
            free(line_copy);
            *num_parts = 0;
            return NULL;
        }
        current_part_idx++;
    }

    free(line_copy); // Libera a cópia da linha
    *num_parts = current_part_idx;
    return parts;
}

/**
 * @brief Libera a memória alocada por `dividirCSV`.
 * @param parts O array de strings a ser liberado.
 * @param num_parts O número de partes no array.
 */
void free_csv_parts(char** parts, int num_parts) {
    if (parts) {
        for (int i = 0; i < num_parts; i++) {
            free(parts[i]);
        }
        free(parts);
    }
}

/**
 * @brief Lê um registro Show de um arquivo CSV baseado no showId.
 * @param targetId O ID do show a ser procurado no CSV.
 * @param pathCSV O caminho para o arquivo CSV.
 * @return Um ponteiro para a estrutura Show alocada dinamicamente, ou NULL se não encontrado ou erro.
 */
Show* lerShow(const char* targetId, const char* pathCSV) {
    FILE *file = fopen(pathCSV, "r");
    if (file == NULL) {
        perror("Erro ao abrir o arquivo CSV");
        return NULL;
    }

    char line_buffer[4096]; // Buffer para ler linhas do CSV (assumindo tamanho máximo de linha)
    fgets(line_buffer, sizeof(line_buffer), file); // Pula a linha do cabeçalho

    Show* s = NULL;
    char** parts = NULL;
    int num_parts = 0;

    while (fgets(line_buffer, sizeof(line_buffer), file) != NULL) {
        // Remove o caractere de nova linha do final da string, se existir
        line_buffer[strcspn(line_buffer, "\n")] = '\0';

        parts = dividirCSV(line_buffer, &num_parts);
        if (parts == NULL || num_parts < 11) {
            // Linha malformada ou erro de alocação. Ignora esta linha e tenta a próxima.
            free_csv_parts(parts, num_parts); // Garante que partes parcialmente alocadas sejam liberadas
            continue;
        }

        if (strcmp(parts[0], targetId) == 0) { // Compara o showId
            s = (Show*)malloc(sizeof(Show)); // Aloca memória para a estrutura Show
            if (s == NULL) {
                perror("Erro de alocação de memória para Show");
                free_csv_parts(parts, num_parts);
                fclose(file);
                return NULL;
            }
            
            // Atribui os campos da estrutura Show, duplicando as strings
            s->showId = strdup(parts[0]);
            s->type = strdup(parts[1]);
            s->title = strdup(parts[2]);
            // Trata campos vazios com "NaN" ou 0
            s->director = (strlen(parts[3]) == 0) ? strdup("NaN") : strdup(parts[3]);
            s->cast = parse_and_sort_string_array(parts[4], &s->num_cast);
            s->country = (strlen(parts[5]) == 0) ? strdup("NaN") : strdup(parts[5]);
            s->dateAdded = (strlen(parts[6]) == 0) ? strdup("NaN") : strdup(parts[6]);
            s->releaseYear = (strlen(parts[7]) == 0) ? 0 : atoi(parts[7]);
            s->rating = (strlen(parts[8]) == 0) ? strdup("NaN") : strdup(parts[8]);
            s->duration = (strlen(parts[9]) == 0) ? strdup("NaN") : strdup(parts[9]);
            s->listedIn = parse_and_sort_string_array(parts[10], &s->num_listedIn);

            free_csv_parts(parts, num_parts); // Libera as partes da linha CSV
            fclose(file);
            return s; // Retorna o Show encontrado
        }
        free_csv_parts(parts, num_parts); // Libera as partes da linha atual antes de ler a próxima
    }

    fclose(file);
    return NULL; // Show não encontrado no arquivo
}

/**
 * @brief Imprime os atributos de uma estrutura Show no formato especificado.
 * @param s Ponteiro para a estrutura Show a ser impressa.
 */
void imprimirShow(const Show *s) {
    if (!s) return; // Não faz nada se o Show for nulo
    printf("%s ## %s ## %s ## %s ## ", s->showId, s->title, s->type, s->director);
    if (s->num_cast == 0) {
        printf("[NaN] ## ");
    } else {
        printf("[");
        for (int i = 0; i < s->num_cast; i++) {
            printf("%s%s", s->cast[i], (i == s->num_cast - 1) ? "" : ", ");
        }
        printf("] ## ");
    }
    printf("%s ## %s ## %d ## %s ## %s ## ", s->country, s->dateAdded, s->releaseYear, s->rating, s->duration);
    if (s->num_listedIn == 0) {
        printf("[NaN] ##\n");
    } else {
        printf("[");
        for (int i = 0; i < s->num_listedIn; i++) {
            printf("%s%s", s->listedIn[i], (i == s->num_listedIn - 1) ? "" : ", ");
        }
        printf("] ##\n");
    }
}

// --- Estrutura do Nó da Árvore AVL ---
typedef struct Node {
    Show *data;         // Dados do Show armazenados no nó
    struct Node *left;  // Ponteiro para o filho esquerdo
    struct Node *right; // Ponteiro para o filho direito
    int height;         // Altura do nó (altura da maior subárvore + 1)
} Node;

// --- Funções da Árvore AVL ---

/**
 * @brief Retorna a altura de um nó. Nós nulos têm altura 0.
 * @param node O nó.
 * @return A altura do nó.
 */
int height(Node *node) {
    if (node == NULL)
        return 0;
    return node->height;
}

/**
 * @brief Retorna o maior de dois inteiros.
 * @param a Primeiro inteiro.
 * @param b Segundo inteiro.
 * @return O maior valor.
 */
int max(int a, int b) {
    return (a > b) ? a : b;
}

/**
 * @brief Cria um novo nó para a árvore AVL, inicializando seus campos.
 * @param s Os dados Show a serem armazenados no novo nó.
 * @return Um ponteiro para o novo nó alocado dinamicamente.
 */
Node* newNode(Show *s) {
    Node* node = (Node*)malloc(sizeof(Node));
    if (node == NULL) { return NULL; } // Erro de alocação
    node->data = s;
    node->left = NULL;
    node->right = NULL;
    node->height = 1; // Um novo nó folha tem altura 1
    return node;
}

/**
 * @brief Realiza uma rotação à direita na subárvore enraizada em 'y'.
 * Usada para rebalancear a árvore quando o fator de balanceamento é > 1 e o filho esquerdo é mais pesado.
 * @param y O nó em que a rotação será realizada.
 * @return O novo nó raiz da subárvore após a rotação.
 */
Node *rightRotate(Node *y) {
    Node *x = y->left;
    Node *T2 = x->right;

    // Executa a rotação
    x->right = y;
    y->left = T2;

    // Atualiza as alturas dos nós afetados
    y->height = max(height(y->left), height(y->right)) + 1;
    x->height = max(height(x->left), height(x->right)) + 1;

    return x; // Retorna a nova raiz da subárvore
}

/**
 * @brief Realiza uma rotação à esquerda na subárvore enraizada em 'x'.
 * Usada para rebalancear a árvore quando o fator de balanceamento é < -1 e o filho direito é mais pesado.
 * @param x O nó em que a rotação será realizada.
 * @return O novo nó raiz da subárvore após a rotação.
 */
Node *leftRotate(Node *x) {
    Node *y = x->right;
    Node *T2 = y->left;

    // Executa a rotação
    y->left = x;
    x->right = T2;

    // Atualiza as alturas dos nós afetados
    x->height = max(height(x->left), height(x->right)) + 1;
    y->height = max(height(y->left), height(y->right)) + 1;

    return y; // Retorna a nova raiz da subárvore
}

/**
 * @brief Calcula o fator de balanceamento de um nó (altura da subárvore esquerda - altura da subárvore direita).
 * @param N O nó para o qual calcular o fator de balanceamento.
 * @return O fator de balanceamento.
 */
int getBalance(Node *N) {
    if (N == NULL)
        return 0;
    return height(N->left) - height(N->right);
}

/**
 * @brief Função recursiva para inserir um Show na árvore AVL.
 * Realiza a inserção como em uma BST comum e, em seguida, balanceia a árvore.
 * @param node O nó atual na recursão.
 * @param s O objeto Show a ser inserido.
 * @return O nó (potencialmente modificado após rotações) para o nível superior.
 */
Node* insertAVL(Node* node, Show* s) {
    // 1. Executa a inserção normal de BST
    if (node == NULL) {
        return newNode(s); // Cria um novo nó e o retorna como a nova raiz da subárvore.
    }

    // Compara o título do Show a ser inserido com o título do nó atual.
    int cmp = strcmp(s->title, node->data->title);
    if (cmp < 0) { // Se o novo show for "menor", insere na subárvore esquerda.
        node->left = insertAVL(node->left, s);
    } else if (cmp > 0) { // Se o novo show for "maior", insere na subárvore direita.
        node->right = insertAVL(node->right, s);
    } else { // Títulos iguais: duplicata.
        // Se houver uma duplicata, libera o Show que seria inserido e retorna o nó existente.
        freeShow(s);
        return node;
    }

    // 2. Atualiza a altura do nó ancestral (o nó atual na recursão).
    node->height = 1 + max(height(node->left), height(node->right));

    // 3. Obtém o fator de balanceamento deste nó ancestral para verificar se ficou desbalanceado.
    int balance = getBalance(node);

    // 4. Se o nó se tornar desbalanceado, há 4 casos a serem tratados com rotações:

    // Caso Esquerda-Esquerda (LL):
    // Desbalanceamento à esquerda (balance > 1) e o novo nó foi inserido na subárvore esquerda do filho esquerdo.
    if (balance > 1 && strcmp(s->title, node->left->data->title) < 0) {
        return rightRotate(node);
    }

    // Caso Direita-Direita (RR):
    // Desbalanceamento à direita (balance < -1) e o novo nó foi inserido na subárvore direita do filho direito.
    if (balance < -1 && strcmp(s->title, node->right->data->title) > 0) {
        return leftRotate(node);
    }

    // Caso Esquerda-Direita (LR):
    // Desbalanceamento à esquerda (balance > 1) e o novo nó foi inserido na subárvore direita do filho esquerdo.
    if (balance > 1 && strcmp(s->title, node->left->data->title) > 0) {
        node->left = leftRotate(node->left); // Rotação à esquerda no filho esquerdo
        return rightRotate(node);            // Rotação à direita no nó atual
    }

    // Caso Direita-Esquerda (RL):
    // Desbalanceamento à direita (balance < -1) e o novo nó foi inserido na subárvore esquerda do filho direito.
    if (balance < -1 && strcmp(s->title, node->right->data->title) < 0) {
        node->right = rightRotate(node->right); // Rotação à direita no filho direito
        return leftRotate(node);             // Rotação à esquerda no nó atual
    }

    // Retorna o ponteiro do nó (inalterado ou a nova raiz da subárvore balanceada).
    return node;
}

/**
 * @brief Pesquisa por um Show pelo título na árvore AVL e imprime o caminho percorrido.
 * @param node O nó atual na recursão (começa com a raiz da árvore).
 * @param title O título do Show a ser pesquisado.
 * @param path_str Um buffer de string onde o caminho será construído.
 * @return true se o Show for encontrado, false caso contrário.
 */
bool searchAVL(Node *node, const char *title, char *path_str) {
    if (node == NULL) {
        strcat(path_str, " NAO"); // Adiciona " NAO" ao caminho se o nó for nulo (não encontrado)
        return false;
    }

    comparisons_count++; // Incrementa o contador de comparações para cada nó visitado
    int cmp = strcmp(title, node->data->title); // Compara o título

    if (cmp == 0) { // Título encontrado
        strcat(path_str, " SIM"); // Adiciona " SIM" ao caminho
        return true;
    } else if (cmp < 0) { // O título é menor, vai para a subárvore esquerda
        strcat(path_str, " esq");
        return searchAVL(node->left, title, path_str);
    } else { // O título é maior, vai para a subárvore direita
        strcat(path_str, " dir");
        return searchAVL(node->right, title, path_str);
    }
}

/**
 * @brief Libera toda a memória alocada para a árvore AVL (nós e dados Show).
 * Percorre a árvore em pós-ordem para liberar os filhos antes do pai.
 * @param node O nó atual na recursão (começa com a raiz da árvore).
 */
void freeAVL(Node *node) {
    if (node != NULL) {
        freeAVL(node->left);
        freeAVL(node->right);
        freeShow(node->data); // Libera os dados Show contidos no nó
        free(node);           // Libera o próprio nó
    }
}

// --- Função Principal ---
int main() {
    Node *root = NULL; // A raiz da árvore AVL, inicialmente nula
    char pathCSV[] = "/tmp/disneyplus.csv"; // Caminho para o arquivo de dados CSV
    char line[1024]; // Buffer para ler linhas da entrada padrão
    char path_output_buffer[4096]; // Buffer para armazenar o caminho de pesquisa

    // Variáveis para medição de tempo
    clock_t start_time, end_time;
    double duration_ms; // Duração em milissegundos

    start_time = clock(); // Inicia o contador de tempo

    // --- Fase de Inserção ---
    // Lê IDs de shows do console até encontrar a linha "FIM".
    // Para cada ID, lê os dados do CSV e insere o Show na árvore AVL.
    while (fgets(line, sizeof(line), stdin) != NULL) {
        line[strcspn(line, "\n")] = 0; // Remove o caractere de nova linha
        if (strcmp(line, "FIM") == 0) {
            break;
        }
        Show *new_show = lerShow(line, pathCSV); // Tenta ler o Show do CSV
        if (new_show) {
            root = insertAVL(root, new_show); // Insere o Show na árvore
        } else {
            // Se lerShow retornar NULL, houve um erro ou o show não foi encontrado.
            fprintf(stderr, "Erro ou Show nao encontrado para ID: %s\n", line);
        }
    }

    // --- Fase de Pesquisa ---
    // Lê títulos de shows da entrada padrão até encontrar a linha "FIM".
    // Para cada título, pesquisa na árvore AVL e imprime o caminho.
    while (fgets(line, sizeof(line), stdin) != NULL) {
        line[strcspn(line, "\n")] = 0; // Remove o caractere de nova linha
        if (strcmp(line, "FIM") == 0) {
            break;
        }
        strcpy(path_output_buffer, "raiz"); // Reinicia o buffer do caminho para cada pesquisa
        searchAVL(root, line, path_output_buffer); // Realiza a pesquisa e constrói o caminho
        printf("%s\n", path_output_buffer); // Imprime o caminho resultante
    }

    end_time = clock(); // Finaliza o contador de tempo
    duration_ms = ((double)(end_time - start_time)) * 1000.0 / CLOCKS_PER_SEC; // Calcula a duração em ms

    // --- Escrita do Arquivo de Log ---
    char matricula[] = "850602"; // Substitua pela sua matrícula real
    char log_filename[256];
    sprintf(log_filename, "%s_arvoreAVL.txt", matricula); // Cria o nome do arquivo de log

    FILE *log_file = fopen(log_filename, "w");
    if (log_file == NULL) {
        perror("Erro ao abrir o arquivo de log");
        return 1; // Retorna com erro
    }
    // Escreve a matrícula, duração (em ms) e o total de comparações no arquivo de log.
    fprintf(log_file, "%s\t%.0f\t%lld\n", matricula, duration_ms, comparisons_count);
    fclose(log_file); // Fecha o arquivo de log

    // --- Liberação de Memória ---
    freeAVL(root); // Libera toda a memória alocada para a árvore e seus dados.

    return 0; // Saída bem-sucedida do programa
}