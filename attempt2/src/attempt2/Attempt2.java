/**
 * Java Code to launch the Second Term Project
 */
package attempt2;
import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Chloe Bautista
 * @author Kevin Corday
 * @author Alex Chheng
 * @author Brenda Solis
 */
public class Attempt2 {

    // Data base info
    static String USER;
    static String PASS;
    static String DBNAME;
    
    /**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("We did it BRODIES!");
    }
    
}
