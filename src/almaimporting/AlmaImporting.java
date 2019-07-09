package almaimporting;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlmaImporting {

    public static Connection connection = null;

    public static void main(String[] args) {

        String connectionString = "jdbc:mysql://35.192.167.130/"
                + "alma_integrations?"
                + "user=root&"
                + "password=F2G4GPF8GyyBLGN7&"
                + "useSSL=false";
        try {
            MySQL_Connection myConnection = new MySQL_Connection(connectionString);
            connection = DriverManager.getConnection(connectionString);
            myConnection.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(AlmaImporting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
