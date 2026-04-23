#include <stdio.h>

int main(){
	char *str = "1,nome,10:00";
	char nome[100];
	int id;
	char hora[6];
	sscanf(str, "%d,%99[^,],%5s", &id, nome, hora); //o do meio significa LEIA ATE ACHAR UMA VIRGULA
	//agora ja temos nome id e hora guardados propriamente
}
