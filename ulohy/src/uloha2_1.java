import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class uloha2_1 {

    //public static int citacPodm = 0;

    public static void main(String[] args) {

        ArrayList<int[]> hrany = new ArrayList<>();
        ArrayList<Integer> vahy = new ArrayList<>();

        int pocetHran = 0;
        int pocetVrcholu = 0;

        try (BufferedReader BR = new BufferedReader(new FileReader(args[0]))) {
            String radka;

            //precti pocet hran a vrcholu
            radka = BR.readLine();
            if (radka != null) {
                pocetVrcholu = Integer.parseInt(radka.split(" ")[2]);
                pocetHran = Integer.parseInt(radka.replace(":", " ").split(" ")[3]);
            } else {
                System.out.println("Spatny format vstupniho souboru.");
                System.exit(1);
            }

            String[] rozsek;

            while ((radka = BR.readLine()) != null) {

                int[] hrana = new int[2];

                rozsek = radka.split(" ");

                hrana[0] = Integer.parseInt(rozsek[0]);
                hrana[1] = Integer.parseInt(rozsek[1]);

                hrany.add(hrana);
                vahy.add(Integer.parseInt(rozsek[2]));
            }

            //vypis mnozinu hran
            vypis_hrany(hrany, pocetHran);

            vypis_vahy(hrany,vahy, pocetHran);

            //vypis podminky
            vypis_podminky(hrany, vahy, pocetHran, pocetVrcholu);

            // minimalizuj
            minimalizuj(hrany, vahy, pocetHran);

            System.out.println("solve;");
            System.out.println("printf \"#OUTPUT: %d\\n\", vahaOdebranych;");
            System.out.println("for {(i,j) in Hrany: x[i,j] == 1} { printf \"%d --> %d\\n\", i, j; }");
            System.out.println("printf \"#OUTPUT END\\n\";");
            System.out.println("end;");

        } catch (IOException e) {
            System.out.println("Soubor " + args[0] + " nenalezen.");
        }


    }

    public static void vypis_hrany(ArrayList<int[]> hrany, int pocetHran) {
        System.out.print("set Hrany := { ");
        for (int i = 0; i < pocetHran - 1; i++) {
            System.out.print("(" + hrany.get(i)[0] + "," + hrany.get(i)[1] + "), ");
        }
        // posledni hrana
        System.out.println("(" + hrany.get(pocetHran - 1)[0] + "," + hrany.get(pocetHran - 1)[1] + ") };");
    }


    public static void vypis_vahy(ArrayList<int[]> hrany,ArrayList<Integer> vahy, int pocetHran) {
        System.out.print("set Vahy := { ");
        for (int i = 0; i < pocetHran - 1; i++) {
            System.out.print("(" + hrany.get(i)[0] + "," + hrany.get(i)[1] + "," + vahy.get(i) + "),");
        }
        // posledni hrana
        System.out.println("(" + hrany.get(pocetHran-1)[0] + "," + hrany.get(pocetHran-1)[1] + "," + vahy.get(pocetHran - 1) +")  };");
    }


    public static void vypis_podminky(ArrayList<int[]> hrany, ArrayList<Integer> vahy, int pocetHran, int pocetVrcholu) {
        // x je pomocna promenna, ktera rika, jestli dana hrana je nebo neni pouzita ve vysledku. Budou oindexovany hranami
        System.out.println("var x{(i,j) in Hrany}, integer, >= 0;");

        //zakaz pro 3
        System.out.println("hranovy_zakaz_3{(i,j) in Hrany, (j,k) in Hrany, (k,l) in Hrany: l == i } : x[i,j] + x[j,k] + x[k,l] >= 1;");

        //zakaz pro 4
        System.out.println("hranovy_zakaz_4{(i,j) in Hrany, (j,k) in Hrany, (k,l) in Hrany, (l,m) in Hrany: m == i } : x[i,j] + x[j,k] + x[k,l] + x[l,m] >= 1;");
    }


    public static void minimalizuj(ArrayList<int[]> hrany, ArrayList<Integer> vahy, int pocetHran) {
        // promenna pro podminku
        System.out.println("var vahaOdebranych;");

        //podminka pro promennou
        System.out.println("pVO: vahaOdebranych >= sum{(i,j) in Hrany, (k,l,m) in Vahy : i==k && j==l} x[i,j] * m;");

        System.out.println("minimize obj: vahaOdebranych;");
    }
}
