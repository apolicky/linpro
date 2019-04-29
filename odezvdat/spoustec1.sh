i="./vstupy1_upr/vstup1-s9.txt"
#for i in vstupy1_upr/vstup1-*
#do
	java -cp ./ulohy/src/ uloha1 $i > doGLP 
	glpsol -m doGLP #|| exit     	
#done
