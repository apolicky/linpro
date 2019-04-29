rm -rf vstupy1_upr

mkdir vstupy1_upr

for i in vstupy/vstup1-*
do
	jmeno=`echo "$i" | cut -f2 -d"/"`
	cat $i | sed 's/^\( \)*//' | sed 's/ -->\( \)*/ /' > vstupy1_upr/$jmeno
done
