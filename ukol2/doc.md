## První praktický domácí úkol

### Sestavení a ovládání

Program pro řešení úlohy je napsán v jazyce Java. Před jejich spuštěním je potřeba spustit JVM a programy zkompilovat. O tyto věci se stará make, který se ve složce nachází.

Program pro řešení úlohy se po kompilaci nazývá
`./prog/u2`. Čte ze standardního vstupu a
vypisuje na standardní výstup. 

Dají se tedy použít jako
`cat file | java -cp ./prog u2`.


### Popis výstupu

Vzhledem k době výpo¤tu jsem úlohu řešil naivně. V grafu jsme hledali nejmenší počet klik, které pokryjí všechny vrcholy. To se dá převést na problém barvení duálního grafu.

Pro každý vrchol si potřebuji pamatovat, jakou barvu jsem mu přiřadil. Na to si vytvořím dvouindexové proměnné `i_bar_j[i,j]` s hodnotami `0/1`. `1` znamená, že vrchol `i` má barvu `j`, pro `0` opačně.

Každý vrchol musí mít právě jednu barvu, proto tedy součet `i_bar_j[i,*] = 1, pro každé i`.

Vytvořím si i jednorozměrné pole s tolika buňkami, jaký očekávám maximální počet barev. Z K&G by to mělo být o 1 víc než je maximální stupeň vrcholu.

Pro každou hranu platí, že danou barvu má právě jeden vrchol. Tedy `i_bar_j[u,k] + i_bar_j[v,k] <= 1, pro každou hranu uv a každou barvu k`.

Snažím se minimalizovat počet použitých barev.

### Co když není řešení?

Myslím si, že tato úloha má vždy nějaké přípustné řešení. Graf se bude dát obarvit vžďy tolika barvami, kolik má vrcholů.
