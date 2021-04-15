
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
                            System.out.println("Listing Teams\n");
                            listTeams(conn);                           
                            break;
                        case 2://Peer Evaluation Averages
                            System.out.println("Peer Evaluation Averages\n");
				averageScore(conn);
                            break;
                        case 3://Outlier Report
                            System.out.println("Outlier Report\n");
				outlierReport(conn);
                            break;
                        case 4://Section Stats
                            System.out.println("Section Stats\n");
				sectionStats(conn);
                            break;
                        case 5://Team Roster
                            System.out.println("Team Roster\n");
				teamRoster(conn);
                            break;
                        case 6://Team Demographics
                            System.out.println("Team Demographics\n");
				teamDemographics(conn);
                            break;
                        case 7://End
                            System.out.println("Exiting program\n");
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
 
    /**
     * list the teams and the students who are in each team.  Order them by department, course number, section number, then team name.
     * @param c 
     */
    public static void listTeams(Connection c)
    {
    /*list the teams and the students who are in each team.  Order them by department, course number, section number, then team name.*/
        String sql = "SELECT teamName,firstName, lastName, departmentName, courseNumber, sectionNumber FROM Team t INNER JOIN Student s ON t.STUDENTID = s.STUDENTID ORDER BY departmentName, courseNumber, sectionNumber, teamName";
            try(
                PreparedStatement stmt = c.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();)//try with resources automatically cleans/closes out resources
            {
                String format ="%-50s%-30s%-30s%-30s%-15s%-15s\n";
                System.out.printf(format,"Team Name", "First Name", "Last Name", "Department Name", "Course Number", "Section Number");
                while (rs.next()) {
                String tname = rs.getString("teamName");
                String fname = rs.getString("firstName");
                String lname = rs.getString("lastName");
                String depName = rs.getString("departmentName");
                int courseNum = rs.getInt("courseNumber");
                int secNum = rs.getInt("sectionNumber");
                
                System.out.printf(format, tname, fname, lname, depName, courseNum, secNum);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting teams");
            ex.printStackTrace();
        }
}
	
    /**
     * For each student in each team, lists the average of the scores that that student received from their peers.  
     * @param c 
     */
    public static void averageScore(Connection c)
    {
        String sql = "SELECT evaluateeID, firstName, lastName, \"AvgScore\" FROM Team t INNER JOIN Student s ON t.STUDENTID = s.STUDENTID INNER JOIN (SELECT evaluateeID, AVG(rating) AS \"AvgScore\" FROM Score sc WHERE evaluatorID != evaluateeID GROUP BY evaluateeID)scr ON s.STUDENTID = evaluateeID";
        try(
        PreparedStatement stmt = c.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();)//try with resources automatically cleans/closes out resources
        {
            String format ="%-50s%-30s%-30s%-30s\n";
            System.out.printf(format,"Student ID", "First Name", "Last Name", "Average Survey Score");
            while (rs.next()) {
                int stdID = rs.getInt("evaluateeID");
                String fname = rs.getString("firstName");
                String lname = rs.getString("lastName");
                double avgScore = rs.getDouble("AvgScore");
                
                System.out.printf(format, stdID, fname, lname, avgScore);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting scores");
            ex.printStackTrace();
        }
    }

    /**
     * Produce a report that shows all the teams whose number of students is > 2 higher or lower than the average number of students in the teams within that particular section
     * @param c 
    */
    public static void outlierReport(Connection c)
    {
        String sql = "SELECT departmentName,courseNumber, sectionNumber, teamName, \"numStudents\" FROM(SELECT te.departmentName, te.courseNumber, te.sectionNumber, te.teamName, COUNT(studentID) AS \"numStudents\", \"average\" FROM Team te INNER JOIN (SELECT departmentName, courseNumber, sectionNumber, AVG(CAST(\"numStudents\" AS DECIMAL(3,2))) AS \"average\" FROM (SELECT departmentName, courseNumber, sectionNumber, teamName, COUNT(studentID) AS \"numStudents\" FROM Team t GROUP BY departmentName, courseNumber, sectionNumber, teamName)a GROUP BY departmentName, courseNumber, sectionNumber)j ON te.DEPARTMENTNAME = j.DEPARTMENTNAME AND te.COURSENUMBER = j.COURSENUMBER AND te.SECTIONNUMBER = j.SECTIONNUMBER GROUP BY te.departmentName, te.courseNumber, te.sectionNumber, teamName,\"average\")a WHERE \"numStudents\" > ( 2+ \"average\") AND \"numStudents\" < ( 2- \"average\")";
        try(
        PreparedStatement stmt = c.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();)//try with resources automatically cleans/closes out resources
        {
            String format ="%-50s%-30s%-30s%-30s\n";
            System.out.printf(format,"Department Name", "Course Number", "Section Number", "Team Name","Number of Students");
            while (rs.next()) {
                String depName = rs.getString("departmentName");
                int courseNum = rs.getInt("courseNumber");
                int secNum = rs.getInt("sectionNumber");
                String tName = rs.getString("teamName");
                int numStud = rs.getInt("numStudents");
                
                System.out.printf(format, depName, courseNum, secNum, tName, numStud);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting outliers");
            ex.printStackTrace();
        }
    }
	
    public static void sectionStats(Connection c)
    {
        //NOT FINISHED YET!
        /**/
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
	
    public static void teamRoster(Connection c)
    {
    //NOT FINISHED YET!
    /**/
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

public static void teamDemographics(Connection c)
{
    //NOT FINISHED YET!
    /**/
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