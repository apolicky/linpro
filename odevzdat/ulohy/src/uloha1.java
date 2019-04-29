import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class uloha1 {

    public static void main(String[] args){

        ArrayList<int[]> hrany = new ArrayList<>();
        int pocetHran;
        int pocetVrcholu;

        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){

            String radka;

            radka = BR.readLine();

            if (radka != null) {

                // verim, ze bude vstup korektni
                pocetVrcholu = Integer.parseInt(radka.split(" ")[1]);
                pocetHran = Integer.parseInt(radka.replace(":", "").split(" ")[2]);

                // precti si hrany
                while ((radka = BR.readLine()) != null) {
                    int[] hrana = new int[2];

                    hrana[0] = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*-->\\s*", " ").split(" ")[0]);
                    hrana[1] = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*-->\\s*", " ").split(" ")[1]);

                    hrany.add(hrana);
                }

                // pro kazdy vrchol si udelam novou promennou, ktera mi reprezentuje ?poradi? v usporadani
                System.out.println("var v{i in (0.." + pocetVrcholu + ")} >=0, <= " + pocetVrcholu + ";");

                // chci minimalizovat tuto hodnotu, zavedu si novou promennou
                System.out.println("var maxHodnota;");
                System.out.println("minimize obj: maxHodnota;");
                System.out.println("iteracni{i in (0.." + pocetVrcholu + ")}: maxHodnota >= v[i]; "); // ta musi mit vyssi hodnotu jak vsechny vrcholy,

                // dva vrcholy vedle sebe musi mit spravnou hodnotu, akorat tedy nevim, jeslti to mam spravne to poradi.
                // je na zacatku usporadani ten, do ktereho jde nejvic hran? Ono je to asi jedno, podle reseni to funguje.
                int cisloPodm = 0;
                for(int i = 0; i < pocetHran; i++){
                    System.out.println("p" + cisloPodm + ": ( v[" + hrany.get(i)[0] + "] - 1 ) >= v[" + hrany.get(i)[1] + "];");
                    cisloPodm++;
                }


                System.out.println("solve;");
                System.out.println("printf \"#OUTPUT: %d\\n\", maxHodnota;");
                System.out.println("printf \"#OUTPUT END\\n\";");
                System.out.println("end;");

            }
            else{
                System.out.println("Spatny format prvniho radku.");
                System.exit(1);
            }


        }
        catch(IOException e){
            System.out.println("Chyba pri cteni ze vstupu. " + e );
            System.exit(1);
        }
    }

}
