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
     /**
     * displays menu
     * @return result that will trigger a menu
     */
    public static int menu()
    {
        Scanner in= new Scanner(System.in);
        int choice;
        System.out.println("\n------Welcome to the second Term Project!------");
        System.out.print("1)List Teams\n2)Peer Evaluation Averages\n3)Outlier Report\n");
        System.out.print("4)Section Stats\n5)Team Roster\n6)Team Demographics");
        choice=in.nextInt();

        return choice;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("We did it BRODIES!");
    }
    
}
