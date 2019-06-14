import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class zkusebni {

    public static void main(String[] args){

        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))) {
            String radka;
            while((radka = BR.readLine()) != null){

                System.out.println(radka.replaceAll("^\\s*", "").replaceAll("\\s*-->\\s*", " ").replaceAll("\\s*\\(\\s*", " ").replaceAll("\\s*\\)\\s*",""));

            }
        }
        catch(IOException e){
            System.out.println("Cybha pri ceteni vstupu. " + e );
        }
    }

}

