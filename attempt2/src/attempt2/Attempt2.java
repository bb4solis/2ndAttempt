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
        System.out.print("4)Section Stats\n5)Team Roster\n6)Team Demographics\n");
        System.out.println("7)End");
        choice=in.nextInt();

        return choice;
    }
    public static void dbSetUP(String user, String pwd,String DBname)
    {   
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBname = in.nextLine();
        System.out.print("Database user name: "); 
        user = in.nextLine();
        System.out.print("Database password: ");
        pwd = in.nextLine();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        int choice;
        Scanner in= new Scanner(System.in);
        Connection conn = null;// initialize the connection
        Statement stmt = null; // initialize the statement
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            choice = menu();
            while(choice != 7){
                try{
                switch(choice){
                        case 1:
                            
                            break;
                        case 2:
                            
                            break;
                        case 3:
                            
                            break;
                        case 4:
                            
                            break;
                        case 5:
                            
                            break;
                        case 6:
                           
                            break;
                        case 7:
                           
                            break;
                                                
			default:
                            System.out.println("\nNot a valid Entery, Enter a valid number from the Menu options\n");
                    }choice=menu();
                    conn.close();
            }catch(InputMismatchException e){
                    System.out.println("\nEnter a Number: \n");
                    in.nextLine();
            }
              catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();  
            } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
         
        
}//ends while
        }//ends try
    }//ends main
            }//ends attempt2
