int main(){
    for(int i=0; i<n; i++){ //passar por cada elemento
        for(int j=0; j<n-i-1; j++){ //passa pelo vetor com 1 elemento
            if(vet[j] < vet[j+1]){
                swap(vet[j], vet[j+1]);
            }
        }
    }
}