echo -n > vysledky_s

for i in ../vstupy/vstup-s*
do
	cat "$i" | java -cp ../prog/ u2 > doGLP
	glpsol -m doGLP | grep -e '#OUTPUT' -e Time >> vysledky_s
done
