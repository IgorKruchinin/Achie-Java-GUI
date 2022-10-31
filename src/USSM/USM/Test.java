package USSM.USM;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        USM prof = new USM("test");
        //prof.create_ssec("Кошка");
        //prof.create_ssec("Собака");
        //prof.gets("Собака").add("Филька");
        System.out.println(prof.gets("Кошка").get(0));
        System.out.println(prof.gets("Собака").get(0));
        try {
            prof.to_archive("testing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
