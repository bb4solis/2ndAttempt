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
    
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
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
    public static void dbSetUP(Scanner in)
    {   
        //Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: "); 
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        int choice;
        Scanner in= new Scanner(System.in);
        dbSetUP(in);
        
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
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
                        case 1://List Teams
                            System.out.println("Listing Teams");
                            listTeams(conn);                           
                            break;
                        case 2://Peer Evaluation Averages
                            
                            break;
                        case 3://Outlier Report
                            
                            break;
                        case 4://Section Stats
                            
                            break;
                        case 5://Team Roster
                            
                            break;
                        case 6://Team Demographics
                           
                            break;
                        case 7://End
                           
                            break;                                                
			default:
                            System.out.println("\nNot a valid Entery, Enter a valid number from the Menu options\n");
                            break;
                    }choice=menu();
                
                    
                }catch(InputMismatchException e){
                    System.out.println("\nEnter a Number: \n");
                    in.nextLine();
                }
                }conn.close();
            } catch (SQLException se) {
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
                }
                 in.close();
            }//end finally try
        System.out.println("Goodbye!");
         
    }//ends main
    
public static void listTeams(Connection c)
{
    //NOT FINISHED YET!
    /*list the teams and the students who are in each team.  Order them by department, course number, section number, then team name.*/
    String sql = "SELECT TeamName FROM Team";
        try(
        PreparedStatement stmt = c.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();)//try with resources automatically cleans/closes out resources
        {
            System.out.println("Teams");
            while (rs.next()) {
                System.out.println(rs.getString("TeamName"));
            }
        } catch (SQLException ex) {
            System.out.println("Error getting teams");
            ex.printStackTrace();
        }
}

/**
 * Insures that a specific Section exists within the Database
 * @param conn
 * @param section
 * @param courseNumber
 */
 public static boolean sectionExists(Connection conn, String courseNumber,String section)
 {
     Statement stmt = null;
     try{
         stmt = conn.createStatement();
         //Todo: actual sql needed to retrieve a section
         String sqlGetSection = "";
         ResultSet rs = stmt.executeQuery(sqlGetSection);
         boolean answer = rs.next();
         rs.close();
         stmt.close();
         return answer;
     }catch (SQLException e) {
            System.out.println("There was a problem accessing the database");
            return false;
	} finally {
            //finally block used to close resources
            try {
		if (stmt != null) {
                    stmt.close();
		}
            } catch (SQLException se2){
                
            }
     }
     
 }
 
  /**
     * Insuring that an Integer is being entered
     * @param str
     * @return 
     */
    public static boolean isNumeric(String str) { 
        try {  
            Integer.parseInt(str);  
            return true;
	} catch(NumberFormatException e){  
            return false;  
	}
    }
    
     /**
     * Insuring that a specified course exists within the Database
     * @param conn
     * @param course
     * @return 
     */
    public static boolean courseExists(Connection conn, String course){
        Statement stmt = null;
	try {
            stmt = conn.createStatement();
            //TODO: sequel to retrieve a course
            String sqlGetCourse = "";
            ResultSet rs = stmt.executeQuery(sqlGetCourse);
            boolean exists = rs.next();
            rs.close();
            stmt.close();
            return exists;
	} catch (SQLException e) {
            System.out.println("There was a problem accessing the database");
            return false;
	} finally {
            //finally block used to close resources
            try {
		if (stmt != null) {
                    stmt.close();
		}
            } catch (SQLException se2){
                
            }
	}
    }
    
}//ends attempt2
