import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class u2 {
    private static int cislo_podminky, pocet_hran, pocet_vrcholu;

    public static void main(String[] args){

        ArrayList<ArrayList<Integer>> vrcholy_sousede = new ArrayList<>();
        ArrayList<ArrayList<Integer>> vrcholy_a_nepratele = new ArrayList<>();

        cislo_podminky = 0;

        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){

            String radka;

            radka = BR.readLine();

            if (radka != null) {

                // verim, ze bude vstup korektni
                pocet_vrcholu = Integer.parseInt(radka.split(" ")[1]);
                pocet_hran = Integer.parseInt(radka.replace(":", "").split(" ")[2]);

                for (int i = 0; i < pocet_vrcholu; i++){
                    vrcholy_sousede.add(new ArrayList<>());
                    vrcholy_a_nepratele.add(new ArrayList<>());
                }

                // precti si hrany
                while ((radka = BR.readLine()) != null) {
                    int v1 = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*--\\s*", " ").split(" ")[0]);
                    int v2 = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*--\\s*", " ").split(" ")[1]);

                    vrcholy_sousede.get(v1).add(v2);
                    vrcholy_sousede.get(v2).add(v1);

                }

                for (int i = 0; i < pocet_vrcholu; i++){
                    Collections.sort(vrcholy_sousede.get(i));
                    for (int j = 0; j < pocet_vrcholu; j++){
                        if (j!=i && !vrcholy_sousede.get(i).contains(j)){
                            vrcholy_a_nepratele.get(i).add(j);
                        }
                    }

                }

                int p1=pocet_vrcholu-1;

                int h = 0; // horni hranice. Bude maximalne o 1 barvu vic, nez je nejvyssi stupen vrcholu.
                for(int i = 0; i < pocet_vrcholu; i++){
                    h = Math.max(vrcholy_a_nepratele.get(i).size(), h);
                    System.out.println("#" + vrcholy_a_nepratele.get(i).size());
                }


                h = Math.min(h,pocet_vrcholu);

                System.out.println("var i_bar_j{i in (0.." + p1 +"), j in (0.." + h + ")} >= 0, <= 1, integer;");

                // chci minimalizovat tuto hodnotu, zavedu si novou promennou

                // kazdy vrchol ma prave jednu barvu
                System.out.println("jed_bar_pro_vrch{i in (0.." + p1 + ")}: sum{j in (0.." + h + ")} i_bar_j[i,j] = 1;");
                System.out.println("var barva_prirazena{i in (0.." + h+ ")} >=0, <=1 ;");
                System.out.println("bar_prir{i in (0.." + p1 + "), j in (0.." + h + ")}:" +
                        " i_bar_j[i,j] <= barva_prirazena[j];");

                // proc by mel byt vrchol 1 v hrane 3?
                System.out.println("vrch_1_v_1{j in (1.." + h + ")}: i_bar_j[0,j] <= 0; ");
                System.out.println("prv_sous_1_ma_1{j in (0.." + h + "): j != 1}: i_bar_j[" + vrcholy_a_nepratele.get(0).get(0) + ",j] <= 0 ;");


                for (int nepritel : vrcholy_a_nepratele.get(0)){
                    System.out.println("nepr" + cislo_podminky + ": i_bar_j[" + nepritel + ", 0] <= 0; ");
                    System.out.println("soused" + cislo_podminky + "{j in (1.." + h +
                            ")}: i_bar_j[0,j] + i_bar_j[" + nepritel + ",j] <= 1; ");
                    cislo_podminky++;
                }

                for (int vrchol = 1; vrchol < pocet_vrcholu; vrchol++) {
                    for (int nepritel : vrcholy_a_nepratele.get(vrchol)) {
                        System.out.println("b" + cislo_podminky + "{j in (0.." + h +
                                ")}: i_bar_j[" + vrchol + ",j] + i_bar_j[" + nepritel + ",j] <= 1;");
                        cislo_podminky++;
                    }

                }

                System.out.println("minimize obj: sum{i in (0.." + p1 + "), j in (0.." + h + ")} j * i_bar_j[i,j];");
                System.out.println("solve;");
                System.out.println("printf \"#OUTPUT: %d\\n\", (max{i in (0.." + p1 + "), j in (0.." + h + "): i_bar_j[i,j] == 1} j) +1 ;");
                System.out.println("for {i in (0.." + p1 +"), j in (0.." + h + "): i_bar_j[i,j] == 1} { printf \"v_%d: %d\\n\", i, j; }");
                System.out.println("printf \"#OUTPUT END\\n\";");
                //System.out.println("for {j in (0.." + h + ")} { printf \"barva_%d: %d\\n\", j, barva_prirazena[j]; } ");
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


