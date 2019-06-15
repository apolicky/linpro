echo -n > vysledky_00

for i in ../vstupy/vstup-00*
do
	cat "$i" | java -cp ../prog/ u2 > doGLP
	glpsol -m doGLP | grep -e '#OUTPUT' -e Time >> vysledky_00
done
