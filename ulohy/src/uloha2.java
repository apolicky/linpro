import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class uloha2 {

    public static int citacPodm = 0;

    public static void main(String[] args){

        ArrayList<int[]> hrany = new ArrayList<>();
        ArrayList<Integer> vahy = new ArrayList<>();

        int pocetHran = 0;
        int pocetVrcholu = 0;

        try(BufferedReader BR = new BufferedReader(new FileReader(args[0]))){
            String radka;

            //precti pocet hran a vrcholu
            radka=BR.readLine();
            if(radka != null){
                pocetVrcholu = Integer.parseInt(radka.split(" ")[2]);
                pocetHran = Integer.parseInt(radka.replace(":"," ").split(" ")[3]);
            }
            else{
                System.out.println("Spatny format vstupniho souboru.");
                System.exit(1);
            }

            String[] rozsek;

            while((radka=BR.readLine())!=null){

                int[] hrana = new int[2];

                rozsek = radka.split(" ");

                hrana[0] = Integer.parseInt(rozsek[0]);
                hrana[1] = Integer.parseInt(rozsek[1]);

                hrany.add(hrana);
                vahy.add(Integer.parseInt(rozsek[2]));
            }

            //vypis mnozinu hran
            vypis_hrany(hrany, pocetHran);

            //vypis podminky
            vypis_podminky(hrany,vahy,pocetHran,pocetVrcholu);


        }
        catch(IOException e){
            System.out.println("Soubor "+args[0]+" nenalezen.");
        }



    }

    public static void vypis_hrany(ArrayList<int[]> hrany, int pocetHran){
        System.out.print("set Hrany := { ");
        for( int i = 0; i < pocetHran - 1; i++){
            System.out.print("\"" + hrany.get(i)[0] + " -> " + hrany.get(i)[1] + "\", ");
        }
        // posledni hrana
        System.out.println("\"" + hrany.get(pocetHran-1)[0] + " -> " + hrany.get(pocetHran-1)[1] +  "\" };");
    }

    public static void vypis_podminky(ArrayList<int[]> hrany, ArrayList<Integer> vahy, int pocetHran, int pocetVrcholu){
        // x je pomocna promenna, ktera rika, jestli dana hrana je nebo neni pouzita ve vysledku. Budou oindexovany hranami
        System.out.println("var x{hrana in Hrany}, integer, >= 0;");

        //vazne nevim, jak primet glpsol, aby to nasel sam, takze to bude takhle
        // najdu si 4 po sobe jdouci hrany a pro ne udelam podminku.
        for (int h1 = 0; h1 < pocetHran; h1++){
            for(int h2 = 0; h2 < pocetHran; h2++){
                // druha hrana musi zacinat tam, kde prvni konci
                if(hrany.get(h1)[1] == hrany.get(h2)[0]){
                    for(int h3 = 0; h3 < pocetHran; h3++){
                        //stejne tak musi treti pokracovat z druhe
                        if(hrany.get(h2)[1] == hrany.get(h3)[0]){
                            //mam najit cykly delky 4 nebo mensi
                            //delka 3, hrana h1 je napojena na hranu h3
                            if (hrany.get(h1)[0] == hrany.get(h3)[1]){
                                vypis_podm_pro_3(hrany.get(h1),hrany.get(h2),hrany.get(h3));
                            }
                            //neni to 3-cyklus, muze to byt jeste 4-cyklus
                            else{
                                for( int h4 = 0; h4 < pocetHran; h4++){
                                    // hrana h1 je napojena na h4

                                    if( hrany.get(h3)[1] == hrany.get(h4)[0] && hrany.get(h4)[1] == hrany.get(h1)[0]){
                                        vypis_podm_pro_4(hrany.get(h1), hrany.get(h2), hrany.get(h3), hrany.get(h4));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // minimalizuj
        minimalizuj(hrany,vahy,pocetHran);

        System.out.println("solve;");
        System.out.println("printf \"#OUTPUT: %d\\n\", vahaOdebranych;");
        System.out.println("for {hrana in Hrany: x[hrana] == 1} { printf \"%s\\n\", hrana; }");
        System.out.println("printf \"#OUTPUT END\\n\";");
        System.out.println("end;");


    }

    public static void vypis_podm_pro_3(int[] h1, int[] h2, int[] h3){
        // musi byt nazev podminky pX?
        // x je ta pom promenna, indikator odebirane hrany
        System.out.println("p"+ citacPodm + ": x[\"" + h1[0] + " -> " + h1[1] +"\"] " +
                                              "+ x[\"" + h2[0] + " -> " + h2[1] + "\"] " +
                                              "+ x[\"" + h3[0] + " -> " + h3[1] + "\"] >= 1;");
        // ty hrany v zavorkach jsou moje indexy
        citacPodm++;
    }

    public static void vypis_podm_pro_4(int[] h1, int[] h2, int[] h3, int[] h4){
        // musi byt nazev podminky pX?
        // x je ta pom promenna, indikator odebirane hrany
        System.out.println("p"+ citacPodm + ": x[\"" + h1[0] + " -> " + h1[1] +"\"] " +
                                                "+ x[\"" + h2[0] + " -> " + h2[1] + "\"] " +
                                                "+ x[\"" + h3[0] + " -> " + h3[1] + "\"] " +
                                                "+ x[\"" + h4[0] + " -> " + h4[1] + "\"] >= 1;");
        // ty hrany v zavorkach jsou moje indexy
        citacPodm++;
    }

    public static void minimalizuj(ArrayList<int[]> hrany, ArrayList<Integer> vahy, int pocetHran){
        // promenna pro podminku
        System.out.println("var vahaOdebranych;");

        //podminka pro promennou
        System.out.print("pVO: vahaOdebranych >= ");
        for(int i = 0; i < pocetHran - 1; i++){
            System.out.print("x[\"" + hrany.get(i)[0] + " -> " + hrany.get(i)[1] + "\"]*" + vahy.get(i) + " + ");
        }
        System.out.println("x[\"" + hrany.get(pocetHran-1)[0] + " -> " + hrany.get(pocetHran-1)[1] + "\"]*" + vahy.get(pocetHran-1) + ";");

        System.out.println("minimize obj: vahaOdebranych;");
    }

}
