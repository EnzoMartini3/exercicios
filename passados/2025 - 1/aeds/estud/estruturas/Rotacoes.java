
No esq(No no){ //graveto dir \\
    No noDir = no.dir;
    No noDirEsq = noDir.esq;
    noDir.esq = no;
    no.dir = noDirEsq;
    return no;
}

No dir(No no){ //graveto esq //
    No noEsq = no.esq;
    No noEsqDir = noEsq.dir;
    noEsq.dir = no;
    no.esq = noEsqDir;
    return no;
}

No esqDir(No no){ //cotovelo esqDir /\
    no.esq = esq(no.esq);
    return dir(no);
} 

No dirEsq(No no){ //cotovelo dirEsq \/
    no.dir = dir(no.dir);
    return esq(no);
} 