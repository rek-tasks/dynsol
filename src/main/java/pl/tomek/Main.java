package pl.tomek;

import java.util.Scanner;
import pl.tomek.utils.SubstringUtils;

public class Main {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("My great app will check if your strings have something in common..");
        while (1>0) {
            System.out.println("Please type string: ");

            String string = myObj.nextLine();

            System.out.println("Please type substring: ");

            String substring = myObj.nextLine();

            String message = SubstringUtils.isSubstring(string, substring) ?
                "Well surely they have: " + substring + " is a substring of " + string :
                "Maybe they have, but surely " + substring + " is not a substring of " + string;

            System.out.println(message);
            System.out.println("Would you like to try again? [type y for yes, and anything else to quit the program]");

            if (!"y".equals(myObj.nextLine())) {
                System.out.println("Please donate this app with multiple recrutation recommendations :)");
                System.exit(0);
            }
        }
    }
}
