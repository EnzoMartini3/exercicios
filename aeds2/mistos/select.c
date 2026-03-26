void select(){
    int menor = 0;

    for(int i=0; i<n-1; i++){
        menor = i;
        for(int j=i+1; j<n; j++){
            if(vet[j] < vet[menor]){
                menor = j;
            }
        }
        swap(vet[menor], vet[i]);
    }
}