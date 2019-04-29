mkdir vstupy2_upr

for i in vstupy/vstup2-*
do
	jmeno=`echo $i | cut -f2 -d'/'`
	cat $i | sed 's/^\( \)*//' | sed 's/ -->\( \)*/ /' | sed 's/\( \)*(\( \)*/ /' | sed 's/\( \)*)//' > vstupy2_upr/$jmeno

done
