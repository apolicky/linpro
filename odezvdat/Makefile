ulohy: uloha1 uloha2
	./uprav1.sh
	./uprav2.sh
	set java11.sh


uloha1: ./ulohy/src/uloha1.java
	javac ./ulohy/src/uloha1.java

uloha2: ./ulohy/src/uloha2_1.java
	javac ./ulohy/src/uloha2_1.java

clean:
	rm ./ulohy/src/*.class doGLP
	rm -r vstupy1_upr vstupy2_upr
