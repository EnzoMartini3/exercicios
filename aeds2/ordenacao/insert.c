int main(){
    int temp, j;
    for(int i=1; i<n; i++){
        temp = vet[i];
        j = i - 1;
        while(j >=0 && vet[j] > temp){
            vet[j+1] = vet[j];
            j--;
        }
        vet[j+1] = temp;
    }
}