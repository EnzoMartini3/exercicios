int main(){
    int menor;

    for(int i=0; i<n-1; i++){
        menor = i;
        for(int j=i+1; j<n; j++){
            if(vet[menor] > vet[j]){
                menor = j;
            }
        }
        swap(vet[i], menor);
    }
}