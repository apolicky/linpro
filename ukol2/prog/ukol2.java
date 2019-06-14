import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;

public class ukol2 {
    private static int cislo_podminky, pocet_hran, pocet_vrcholu;

    public static void main(String[] args){

        ArrayList

        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){

            String radka;

            radka = BR.readLine();

            if (radka != null) {

                // verim, ze bude vstup korektni
                pocet_vrcholu = Integer.parseInt(radka.split(" ")[1]);
                pocet_hran = Integer.parseInt(radka.replace(":", "").split(" ")[2]);

                // precti si hrany
                while ((radka = BR.readLine()) != null) {
                    v1 = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*--\\s*", " ").split(" ")[0]);
                    v2 = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*--\\s*", " ").split(" ")[1]);

                    if(vrcholy_a_kamosi.containsKey(hrana[0])){
                        ArrayList l = vrcholy_a_kamosi.get(hrana[0]);
                        if(!l.contains(hrana[1])){
                            l.add(hrana[1]);
                        }
                    }
                    else {
                        ArrayList<Integer> l = new ArrayList<>();
                        l.add(hrana[1]);
                        vrcholy_a_kamosi.put(hrana[0],l);
                    }

                    if(vrcholy_a_kamosi.containsKey(hrana[1])){
                        ArrayList l = vrcholy_a_kamosi.get(hrana[1]);
                        if(!l.contains(hrana[0])){
                            l.add(hrana[0]);
                        }
                    }
                    else {
                        ArrayList<Integer> l = new ArrayList<>();
                        l.add(hrana[0]);
                        vrcholy_a_kamosi.put(hrana[1],l);
                    }
                }

                // pro kazdy vrchol si udelam novou promennou, ktera mi reprezentuje ?poradi? v usporadani
                System.out.println("var v{i in (0.." + (pocet_vrcholu-1) + ")} >=0;");

                // chci minimalizovat tuto hodnotu, zavedu si novou promennou
                System.out.println("var pocSkupin;");
                System.out.println("minimize obj: pocSkupin;");
                System.out.println("iteracni{i in (0.." + (pocet_vrcholu-1) + ")}: pocSkupin >= v[i]; "); // ta musi mit vyssi hodnotu jak vsechny vrcholy,

                // dva vrcholy vedle sebe musi mit spravnou hodnotu, akorat tedy nevim, jeslti to mam spravne to poradi.
                // je na zacatku usporadani ten, do ktereho jde nejvic hran? Ono je to asi jedno, podle reseni to funguje.

                cislo_podminky = 0;
                for(int i = 0; i < pocet_vrcholu; i++){
                    System.out.println("# size of k_prolezeni: " + k_prolezeni.size());
                    while(k_prolezeni.size() > 0 ){
                        int dalsi = k_prolezeni.remove(0);
                        System.out.println("# neprazdna fronta, jsem " + dalsi);
                        vypis_se(dalsi);
                    }

                    if(!byly_vypsany.contains(i)){
                        vypis_se(i);
                    }
                }


                System.out.println("solve;");
                System.out.println("printf \"#OUTPUT: %d\\n\", pocSkupin;");
                System.out.println("for {i in (0.." + (pocet_vrcholu-1) +")} { printf \"v_%d: %d\\n\", i, v[i]; }");
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

    public static void vypis_se(int vrchol){
        ArrayList l = vrcholy_a_kamosi.get(vrchol);
        byly_vypsany.add(vrchol);
        System.out.println("# \"vypisou se lide, se kterymi se bavit nemuzu \", " + vrchol);
        for (int kamos = vrchol + 1; kamos < pocet_vrcholu; kamos++) {
            if (!l.contains(kamos) && !soused_souseda(kamos)) {
                System.out.println("p" + cislo_podminky + ": v[" + vrchol + "] + 1 <= v[" + kamos + "];");
                cislo_podminky++;
            }
            else {
                if (!byly_vypsany.contains(kamos) && !k_prolezeni.contains(kamos)) {
                    k_prolezeni.add(kamos);
                }
            }
        }
    }

    public static boolean soused_souseda(int vrchol) {

    }

}

