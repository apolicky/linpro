set java11.sh

#for i in vstupy2_upr/vstup2-*
#do
i="vstupy2_upr/vstup2-000.txt"
#i="z2"
	java -cp ./ulohy/src/ uloha2_1 $i > doGLP

	#glpsol -m doGLP || exit
#done
