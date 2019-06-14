import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class u2_1 {
    public static void main(String[] args){
        int cislo_podminky, pocet_hran, pocet_vrcholu;
        int[][] hrany;

        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){

            String radka;

            radka = BR.readLine();

            if (radka != null) {

                // verim, ze bude vstup korektni
                pocet_vrcholu = Integer.parseInt(radka.split(" ")[1]);
                pocet_hran = Integer.parseInt(radka.replace(":", "").split(" ")[2]);

                hrany = new int[pocet_vrcholu][pocet_vrcholu];

                // precti si hrany
                while ((radka = BR.readLine()) != null) {
                    int i = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*--\\s*", " ").split(" ")[0]);
                    int j = Integer.parseInt(radka.replaceAll("^\\s*", "").replaceAll("\\s*--\\s*", " ").split(" ")[1]);

                    /*if(i<j){
                        hrany[i][j] = 1;
                    }
                    else{
                        hrany[j][i] = 1;
                    }*/
                    System.out.println("("+ i + ", " + j+ ")");

                }

                /*
                for (int i = 0; i < pocet_vrcholu; i++){
                    for (int j = i; j< pocet_vrcholu;j++){
                        System.out.print(hrany[i][j]);
                    }
                    System.out.println();
                }


                */
                //vypis_hrany(hrany,pocet_vrcholu);

                /*
                System.out.println("param poc_vrcholu := " + (pocet_vrcholu - 1) + ";");
                System.out.println("var barva_prirazena{i in (0..poc_vrcholu)} >=0;");
                System.out.println("var x_i_b{i in (0..poc_vrcholu), b in (0..poc_vrcholu)} >=0; ");
                System.out.println("var poc_skupin;");
                System.out.println("minimize obj: sum{i in (0..poc_vrcholu)} barva_prirazena[i];");

                System.out.println("barev_u_vrcholu{i in (0..poc_vrcholu)}: sum{b in (0..poc_vrcholu)} x_i_b[i,b] = 1;");
                System.out.println("spojene_vrcholy_jine_barvy{(i,j) in Hrany, b in (0..poc_vrcholu)}: x_i_b[i,b] + x_i_b[j,b] <= 1;");
                System.out.println("priradil_jsem_barvu{i in (0..poc_vrcholu), b in (0..poc_vrcholu)}: x_i_b[i,b] <= barva_prirazena[b];");

                System.out.println("solve;");
                System.out.println("printf \"#OUTPUT: %d\\n\", sum{i in (0..poc_vrcholu)} barva_prirazena[i] + 1;");
                System.out.println("for {i in (0..poc_vrcholu), b in (0..poc_vrcholu): x_i_b[i,b] == 1} { printf \"v_%d: %d\\n\", i, b; }");
                System.out.println("for {i in (0..poc_vrcholu)} { printf \"barva_prirazena[%d] %d\\n\", i, barva_prirazena[i]; }");
                System.out.println("printf \"#OUTPUT END\\n\";");
                System.out.println("end;");
                */
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

    public static void vypis_hrany(int[][] hrany, int pocet_vrcholu) {

        ArrayList<int[]> nove_hrany = new ArrayList<>();

        for (int i = 0; i < pocet_vrcholu - 1; i++){
            for(int j = i + 1 ; j < pocet_vrcholu - 1; j++){
                if(hrany[i][j] != 1){
                    int[] hrana = new int[2];
                    hrana[0] = i;
                    hrana[1] = j;

                    nove_hrany.add(hrana);
                }
            }
        }

        System.out.print("set Hrany := { ");
        for (int i = 0; i < nove_hrany.size()-1; i++) {
            System.out.print("(" + nove_hrany.get(i)[0] + "," + nove_hrany.get(i)[1] + "), ");
        }
        // posledni hrana
        System.out.println("(" + nove_hrany.get(nove_hrany.size() - 1)[0] + "," + nove_hrany.get(nove_hrany.size()- 1)[1] + ") };");
    }

}

