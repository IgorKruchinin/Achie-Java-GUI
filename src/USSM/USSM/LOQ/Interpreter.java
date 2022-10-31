package USSM.USSM.LOQ;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {
        LOQ lang = new LOQ();
        if (args.length < 1) {
            while (true) {
                System.out.print(lang.getProfName() + " > ");
                Scanner scanner = new Scanner(System.in);
                try {
                    lang.parseQuery(scanner.nextLine());
                } catch (LOQNoProfileException e) {
                    System.out.println(e.getMessage());
                }
                if (lang.changed()) {
                    switch (lang.getLastFormat()) {
                        case 0:
                            System.out.println(lang.popInt());
                            break;
                        case 1:
                            System.out.println(lang.popStr());
                            break;
                        case 3:
                            System.out.println(lang.getLockStatus() ? "locked" : "unlocked");
                    }
                }
            }
        } else {
            in_file(lang, args[0]);
        }
    }
    public static void in_file(LOQ lang, String filename) {
        Path path = Paths.get(filename);
        try {
            for (String s : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                try {
                    lang.parseQuery(s);
                } catch (LOQNoProfileException e) {
                    System.out.println(e.getMessage());
                }
                if (lang.changed()) {
                    switch (lang.getLastFormat()) {
                        case 0:
                            System.out.println(lang.popInt());
                            break;
                        case 1:
                            System.out.println(lang.popStr());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot open file named " + filename);
        }
    }
}
