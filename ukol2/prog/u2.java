import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class u2 {
    private static int cislo_podminky, pocet_hran, pocet_vrcholu, dalsi_barva;
    private static int h; // horni hranice. Bude maximalne o 1 barvu vic, nez je nejvyssi stupen vrcholu.

    public static void main(String[] args){

        ArrayList<ArrayList<Integer>> vrcholy_sousede = new ArrayList<>();
        ArrayList<ArrayList<Integer>> vrcholy_a_nepratele = new ArrayList<>();

        cislo_podminky = 0;
        dalsi_barva = 0;
        h = 0;

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


                for(int i = 0; i < pocet_vrcholu; i++){
                    h = Math.max(vrcholy_a_nepratele.get(i).size(), h);
                    System.out.println("#" + vrcholy_a_nepratele.get(i).size());
                }

                for (int i = 0 ; i < vrcholy_a_nepratele.size(); i++){
                    System.out.print("# v_" + i);
                    for(int soused : vrcholy_a_nepratele.get(i) ){
                        System.out.print(" " + soused);
                    }
                    System.out.println();
                }


                h = Math.min(h,pocet_vrcholu);

                System.out.println("var i_bar_j{i in (0.." + p1 +"), j in (0.." + h + ")} >= 0, <= 1, integer;");

                // chci minimalizovat tuto hodnotu, zavedu si novou promennou

                // kazdy vrchol ma prave jednu barvu
                System.out.println("jed_bar_pro_vrch{i in (0.." + p1 + ")}: sum{j in (0.." + h + ")} i_bar_j[i,j] = 1;");
                System.out.println("var barva_prirazena{i in (0.." + h+ ")} >=0, <=1 ;");
                System.out.println("bar_prir{i in (0.." + p1 + "), j in (0.." + h + ")}:" +
                        " i_bar_j[i,j] <= barva_prirazena[j];");


                ArrayList<Integer> jista_barva = new ArrayList<>();

                // KDYZ SE TADY PREHODI KOMENTARE U projdi_prvni a for cyklu in nepri...,
                // ZRYCHLI SE VYPOCET, AVSAK JEN U NEKTERYCH VSTUPU DA VYSLEDEK JAKO JE V RESENICH

                /*
                projdi_prvni(0,jista_barva,vrcholy_a_nepratele);
                */
                ///*

                System.out.println("vrch_1_v_1{j in (0.." + h + "): j != " + dalsi_barva + "}: i_bar_j[0,j] <= 0; ");
                dalsi_barva++;
                int pn0 = vrcholy_a_nepratele.get(0).get(0); // prvni nepritel nulteho vrcholu
                System.out.println("prv_sous_1_ma_1{j in (0.." + h + "): j != "+dalsi_barva+"}: i_bar_j[" + pn0 + ",j] <= 0 ;");
                dalsi_barva++;
                //*/

                // KONEC CASTI S KOMENTARI

                //  HORNI LIMIT NA BARVU i. VRCHOLU

                /*for(int i = 0; i < vrcholy_a_nepratele.size(); i++){
                    System.out.println("hl" + cislo_podminky + "{j in (" + (vrcholy_a_nepratele.get(i).size() - 1) +
                            ".." + h + ")}: i_bar_j[" + i + ",j] <= 0 ; " );
                    cislo_podminky++;
                }*/

                for (int i = 0; i < vrcholy_a_nepratele.size(); i++){
                    System.out.println("hl" + cislo_podminky + "{j in (" + (i+1) +
                            ".." + h + ")}: i_bar_j[" + i + ",j] <= 0 ; " );
                    cislo_podminky++;
                }

                // KONEC LIMITU

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

    public static void projdi_prvni(int vrchol, ArrayList<Integer> maji_jistou_barvu, ArrayList<ArrayList<Integer>> vrch_nepr){
        /*
         System.out.println("vrch_1_v_1{j in (0.." + h + "): j != " + dalsi_barva + "}: i_bar_j[0,j] <= 0; ");
                jista_barva.add(0);
                dalsi_barva++;
                int pn0 = vrcholy_a_nepratele.get(0).get(0); // prvni nepritel nulteho vrcholu
                System.out.println("prv_sous_1_ma_1{j in (0.." + h + "): j != "+dalsi_barva+"}: i_bar_j[" + pn0 + ",j] <= 0 ;");
                dalsi_barva++;
                jista_barva.add(pn0);

                projdi_prvni(0,jista_barva);
         */


        System.out.println("vrch_1_v_" + vrchol + "{j in (0.." + h + "): j != " + dalsi_barva + "}: i_bar_j[" + vrchol + ",j] <= 0; ");
        maji_jistou_barvu.add(vrchol);
        dalsi_barva++;
        cislo_podminky++;


        int dalsi_vrchol = -1;
        for( int dv : vrch_nepr.get(vrchol)){
            if(!maji_jistou_barvu.contains(dv)){
                boolean je_u_vsech_nepratel = true;
                for(int i = 0; i < maji_jistou_barvu.size(); i++){
                    if(!vrch_nepr.get(maji_jistou_barvu.get(i)).contains(dv)){
                        je_u_vsech_nepratel = false;
                    }
                }
                if(je_u_vsech_nepratel){
                    dalsi_vrchol=dv;
                    break;
                }
            }


        }

        if(dalsi_vrchol != -1){
            projdi_prvni(dalsi_vrchol,maji_jistou_barvu,vrch_nepr);
        }
    }


}


